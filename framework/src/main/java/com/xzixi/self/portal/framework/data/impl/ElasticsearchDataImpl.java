package com.xzixi.self.portal.framework.data.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzixi.self.portal.framework.data.IBaseData;
import com.xzixi.self.portal.framework.data.ISearchEngine;
import com.xzixi.self.portal.framework.exception.ProjectException;
import com.xzixi.self.portal.framework.exception.ServerException;
import com.xzixi.self.portal.framework.lock.ILock;
import com.xzixi.self.portal.framework.lock.impl.LocalLock;
import com.xzixi.self.portal.framework.mapper.IBaseMapper;
import com.xzixi.self.portal.framework.model.BaseModel;
import com.xzixi.self.portal.framework.model.search.Pagination;
import com.xzixi.self.portal.framework.model.search.QueryParams;
import com.xzixi.self.portal.framework.util.OrderUtils;
import com.xzixi.self.portal.framework.util.ReflectUtils;
import com.xzixi.self.portal.framework.util.TypeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

import static com.xzixi.self.portal.framework.model.search.QueryParams.SCORE;
import static com.xzixi.self.portal.framework.util.TypeUtils.parseObject;

/**
 * elasticsearch实现
 *
 * @author 薛凌康
 */
public class ElasticsearchDataImpl<M extends IBaseMapper<T>, T extends BaseModel> extends ServiceImpl<M, T>
        implements IBaseData<T>, ISearchEngine {

    private static final String DEFAULT_SYNC_DATA_ORDER = "id asc";
    private Class<T> clazz;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @SuppressWarnings("unchecked")
    public ElasticsearchDataImpl() {
        // 获取T的实际类型，第二个泛型参数
        this.clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        // 获取Document注解
        Document document = clazz.getDeclaredAnnotation(Document.class);
        if (document == null) {
            throw new ProjectException(String.format("类(%s)必须使用@Document注解！", clazz.getName()));
        }
    }

    /**
     * 获取删除操作锁
     *
     * @return ILock
     */
    protected ILock getRemoveLock() {
        return new LocalLock();
    }

    /**
     * 获取初始化操作锁
     *
     * @return ILock
     */
    protected ILock getInitLock() {
        return new LocalLock();
    }

    /**
     * 获取同步操作锁
     *
     * @return ILock
     */
    protected ILock getSyncLock() {
        return new LocalLock();
    }

    /**
     * 同步数据的顺序，必须是数据插入先后的升序
     *
     * @return 例如 "id asc"，自增id新插入的数据会排在后面
     */
    protected String syncDataOrder() {
        return DEFAULT_SYNC_DATA_ORDER;
    }

    @Override
    public T getById(Serializable id) {
        GetQuery getQuery = new GetQuery();
        getQuery.setId(String.valueOf(id));
        return elasticsearchTemplate.queryForObject(getQuery, clazz);
    }

    @Override
    public Collection<T> listByIds(Collection<? extends Serializable> idList) {
        Collection<String> ids = idList.stream().map(String::valueOf).collect(Collectors.toList());
        SearchQuery query = new NativeSearchQueryBuilder().withIds(ids).build();
        return elasticsearchTemplate.queryForList(query, clazz);
    }

    @Override
    public List<T> list(QueryParams<T> params) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(parseQueryBuilder(params, true));
        List<SortBuilder<?>> sortBuilders = parseSortBuilders(params);
        if (CollectionUtils.isNotEmpty(sortBuilders)) {
            sortBuilders.forEach(builder::withSort);
        }
        return elasticsearchTemplate.queryForList(builder.build(), clazz);
    }

    @Override
    public Pagination<T> page(Pagination<T> pagination, QueryParams<T> params) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(parseQueryBuilder(params, true));
        List<SortBuilder<?>> sortBuilders = parseSortBuilders(params);
        if (CollectionUtils.isNotEmpty(sortBuilders)) {
            sortBuilders.forEach(builder::withSort);
        }
        builder.withPageable(parsePageRequest(pagination));
        AggregatedPage<T> page = elasticsearchTemplate.queryForPage(builder.build(), clazz);
        pagination.setCurrent(page.getNumber() + 1);
        pagination.setSize(page.getSize());
        pagination.setTotal(page.getTotalElements());
        pagination.setRecords(page.getContent());
        return pagination;
    }

    @Override
    public long count(QueryParams<T> params) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(parseQueryBuilder(params, true));
        return elasticsearchTemplate.count(builder.build(), clazz);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(T model) {
        if (!super.save(model)) {
            throw new ServerException(model, "数据库写入失败！");
        }
        index(model);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<T> models, int batchSize) {
        if (!super.saveBatch(models, batchSize)) {
            throw new ServerException(models, "数据库写入失败！");
        }
        index(models, batchSize);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(T model) {
        if (!super.saveOrUpdate(model)) {
            throw new ServerException(model, "数据库写入失败！");
        }
        index(model);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateBatch(Collection<T> models, int batchSize) {
        if (!super.saveOrUpdateBatch(models, batchSize)) {
            throw new ServerException(models, "数据库写入失败！");
        }
        index(models, batchSize);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(T model) {
        if (!super.updateById(model)) {
            throw new ServerException(model, "数据库写入失败！");
        }
        index(model);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchById(Collection<T> models, int batchSize) {
        if (!super.updateBatchById(models, batchSize)) {
            throw new ServerException(models, "数据库写入失败！");
        }
        index(models, batchSize);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        // 删除操作不等待初始化和同步，冲突直接返回异常
        // 检查是否正在进行初始化或同步
        checkInit();
        checkSync();
        String node = randomString();
        try {
            // 挂载节点
            getRemoveLock().mount(node);
            // 二次检查
            checkInit();
            checkSync();
            // 执行删除
            return doRemoveById(id);
        } finally {
            getRemoveLock().unmount(node);
        }
    }

    private boolean doRemoveById(Serializable id) {
        if (!super.removeById(id)) {
            throw new ServerException(id, "数据库写入失败！");
        }
        remove(QueryBuilders.idsQuery().addIds(String.valueOf(id)));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        // 检查是否正在进行初始化或同步
        checkInit();
        checkSync();
        String node = randomString();
        try {
            // 挂载节点
            getRemoveLock().mount(node);
            // 二次检查
            checkInit();
            checkSync();
            // 执行删除
            return doRemoveByIds(idList);
        } finally {
            getRemoveLock().unmount(node);
        }
    }

    private boolean doRemoveByIds(Collection<? extends Serializable> idList) {
        if (!super.removeByIds(idList)) {
            throw new ServerException(idList, "数据库写入失败！");
        }
        String[] ids = idList.stream().map(String::valueOf).toArray(String[]::new);
        remove(QueryBuilders.idsQuery().addIds(ids));
        return true;
    }

    @Override
    public int defaultBatchSize() {
        return 1000;
    }

    @Override
    public void init() {
        // 初始化操作不等待同步操作，冲突就返回异常，如果有未完成的删除操作就等待
        // 检查是否正在进行同步
        checkInit();
        checkSync();
        String value = randomString();
        try {
            // 获取锁
            if (!getInitLock().acquire(value)) {
                // 竞争失败
                throwInit();
            }
            // 二次检查
            checkSync();
            // 等待删除操作完成后执行初始化
            getRemoveLock().waitNode(this::doInit);
        } finally {
            getInitLock().release(value);
        }
    }

    /**
     * 执行初始化
     */
    private void doInit() {
        // 删除索引，同时会清空数据
        elasticsearchTemplate.deleteIndex(clazz);
        // 创建索引和映射
        elasticsearchTemplate.createIndex(clazz);
        elasticsearchTemplate.putMapping(clazz);
        // 批量导入数据库的数据
        boolean isLastPage = false;
        int size = defaultBatchSize();
        int current = 1;
        while (!isLastPage) {
            IPage<T> page = super.page(new Page<T>(current, size).addOrder(OrderItem.asc("id")));
            List<T> list = page.getRecords();
            index(list, size);
            // 已经导入的数据个数
            isLastPage = (current - 1) * size + list.size() >= page.getTotal();
            current++;
        }
    }

    @Override
    public void sync() {
        // 同步操作不等待初始化操作，冲突就返回异常，如果有未完成的删除操作就等待
        // 检查是否正在进行初始化
        checkInit();
        checkSync();
        String value = randomString();
        try {
            // 获取锁
            if (!getSyncLock().acquire(value)) {
                // 竞争失败
                throwSync();
            }
            // 二次检查
            checkInit();
            // 等待删除操作完成后执行同步
            getRemoveLock().waitNode(this::doSync);
        } finally {
            getSyncLock().release(value);
        }
    }

    private void doSync() {
        // 创建索引和映射
        elasticsearchTemplate.createIndex(clazz);
        elasticsearchTemplate.putMapping(clazz);
        // 批量比对数据
        List<T> forIndex = new ArrayList<>();
        List<T> forRemove = new LinkedList<>();
        // 以这个总数为准，防止误删除同步期间新增的数据
        long totalInDb = super.count();
        long totalInEs = countAll();
        boolean isLastPageInDb = false;
        boolean isLastPageInEs = false;
        int size = defaultBatchSize();
        int current = 1;
        // 数据库或搜索引擎有一个查询到了最后一页就退出循环
        while (!isLastPageInDb && !isLastPageInEs) {
            // 分页查询数据库
            IPage<T> pageInDb = super.page(new Page<T>(current, size).addOrder(getSyncOrderItem()));
            List<T> modelsInDb = pageInDb.getRecords();
            isLastPageInDb = (current - 1) * size + modelsInDb.size() >= totalInDb;
            if (isLastPageInDb) {
                // 如果到了最后一页，对modelsInDb裁剪，防止误操作新增的数据
                long expectSize = totalInDb - (current - 1) * size;
                if (modelsInDb.size() > expectSize) {
                    modelsInDb = modelsInDb.subList(0, (int) expectSize);
                }
            }
            // 分页查询搜索引擎
            Pagination<T> pageInEs = page(new Pagination<T>(current, size).orders(getSyncOrder()), new QueryParams<>());
            List<T> modelsInEs = pageInEs.getRecords();
            isLastPageInEs = (current - 1) * size + modelsInEs.size() >= totalInEs;
            if (isLastPageInEs) {
                // 如果到了最后一页，对modelsInEs裁剪，防止误操作新增的数据
                long expectSize = totalInEs - (current - 1) * size;
                if (modelsInEs.size() > expectSize) {
                    modelsInEs = modelsInEs.subList(0, (int) expectSize);
                }
            }
            // 遍历数据库的数据，查询出搜索引擎中缺少或需要更新的元素，添加到forIndex集合
            List<T> finalModelsInEs = modelsInEs;
            modelsInDb.forEach(model -> {
                // 先从forRemove查询，再从recordsInEs查询
                T matchModel = matchModel(forRemove, model);
                if (matchModel != null) {
                    forRemove.remove(matchModel);
                }
                if (matchModel == null) {
                    matchModel = matchModel(finalModelsInEs, model);
                }
                // 先比较hashCode，再比较equals
                if (matchModel == null || model.hashCode() != matchModel.hashCode() || !model.equals(matchModel)) {
                    forIndex.add(model);
                }
            });
            // 查询在recordsInEs存在，在recordsInDb不存在的元素，添加到forRemove集合
            List<Integer> idsInDb = modelsInDb.stream().map(BaseModel::getId).collect(Collectors.toList());
            List<T> modelsNotInDb = modelsInEs.stream().filter(model -> !idsInDb.contains(model.getId())).collect(Collectors.toList());
            forRemove.addAll(modelsNotInDb);
            current++;
        }
        while (!isLastPageInDb) {
            // 数据库没有到最后一页，查询剩余的记录，添加到forIndex集合
            IPage<T> pageInDb = super.page(new Page<T>(current, size).addOrder(getSyncOrderItem()));
            List<T> modelsInDb = pageInDb.getRecords();
            isLastPageInDb = (current - 1) * size + modelsInDb.size() >= totalInDb;
            if (isLastPageInDb) {
                // 如果到了最后一页，对modelsInDb裁剪，防止误操作新增的数据
                long expectSize = totalInDb - (current - 1) * size;
                if (modelsInDb.size() > expectSize) {
                    modelsInDb = modelsInDb.subList(0, (int) expectSize);
                }
            }
            forIndex.addAll(modelsInDb);
            current++;
        }
        while (!isLastPageInEs) {
            // 搜索引擎没有到最后一页，查询剩余的记录，添加到forRemove集合
            Pagination<T> pageInEs = page(new Pagination<T>(current, size).orders(getSyncOrder()), new QueryParams<>());
            List<T> modelsInEs = pageInEs.getRecords();
            isLastPageInEs = (current - 1) * size + modelsInEs.size() >= totalInEs;
            if (isLastPageInEs) {
                // 如果到了最后一页，对modelsInEs裁剪，防止误操作新增的数据
                long expectSize = totalInEs - (current - 1) * size;
                if (modelsInEs.size() > expectSize) {
                    modelsInEs = modelsInEs.subList(0, (int) expectSize);
                }
            }
            forRemove.addAll(modelsInEs);
            current++;
        }
        if (CollectionUtils.isNotEmpty(forIndex)) {
            // 索引数据
            index(forIndex, size);
        }
        if (CollectionUtils.isNotEmpty(forRemove)) {
            // 删除数据
            String[] ids = forRemove.stream().map(model -> String.valueOf(model.getId())).toArray(String[]::new);
            remove(QueryBuilders.idsQuery().addIds(ids));
        }
    }

    private T matchModel(List<T> models, T model) {
        return models.stream().filter(matchModel -> matchModel.getId().equals(model.getId())).findFirst().orElse(null);
    }

    private OrderItem getSyncOrderItem() {
        String[] arr = OrderUtils.parse(syncDataOrder());
        if (arr == null || ArrayUtils.isEmpty(arr)) {
            arr = OrderUtils.parse(DEFAULT_SYNC_DATA_ORDER);
        }
        assert arr != null;
        if (OrderUtils.isAsc(arr[1])) {
            return OrderItem.asc(arr[0]);
        }
        return OrderItem.desc(arr[0]);
    }

    private String getSyncOrder() {
        String[] arr = OrderUtils.parse(syncDataOrder());
        if (arr == null || ArrayUtils.isEmpty(arr)) {
            return DEFAULT_SYNC_DATA_ORDER;
        }
        return syncDataOrder();
    }

    /**
     * 检查是否正在进行初始化
     */
    private void checkInit() {
        if (getInitLock().check()) {
            throwInit();
        }
    }

    /**
     * 检查是否正在进行同步
     */
    private void checkSync() {
        if (getSyncLock().check()) {
            throwSync();
        }
    }

    private void throwInit() {
        throw new ServerException("搜索引擎正在进行初始化，请稍后再试！");
    }

    private void throwSync() {
        throw new ServerException("搜索引擎正在进行同步，请稍后再试！");
    }

    private void index(T entity) {
        try {
            IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(String.valueOf(entity.getId()))
                .withObject(entity)
                .build();
            elasticsearchTemplate.index(indexQuery);
        } catch (Exception e) {
            throw new ServerException(entity, "搜索引擎写入失败！", e);
        }
    }

    private void index(Collection<T> entityList, int batchSize) {
        try {
            List<T> entities = new ArrayList<>(entityList);
            int fromIndex = 0;
            int toIndex = fromIndex + batchSize;
            while (fromIndex < entities.size()) {
                if (toIndex > entities.size()) {
                    toIndex = entities.size();
                }
                List<IndexQuery> queries = entities.subList(fromIndex, toIndex).stream()
                        .map(entity -> new IndexQueryBuilder()
                                .withId(String.valueOf(entity.getId())).withObject(entity).build())
                        .collect(Collectors.toList());
                elasticsearchTemplate.bulkIndex(queries);
                fromIndex = toIndex;
                toIndex += batchSize;
            }
        } catch (Exception e) {
            throw new ServerException(entityList, "搜索引擎写入失败！", e);
        }
    }

    private void remove(QueryBuilder queryBuilder) {
        try {
            DeleteQuery deleteQuery = new DeleteQuery();
            deleteQuery.setQuery(queryBuilder);
            elasticsearchTemplate.delete(deleteQuery, clazz);
        } catch (Exception e) {
            throw new ServerException(queryBuilder, "搜索引擎写入失败！", e);
        }
    }

    private PageRequest parsePageRequest(Pagination<T> pagination) {
        // PageRequest的第一页从0开始
        int page = (int) (pagination.getCurrent() - 1);
        int size = (int) pagination.getSize();
        String[] orders = pagination.getOrders();
        // 排序条件
        List<Sort.Order> orderList = new ArrayList<>();
        if (orders != null && ArrayUtils.isNotEmpty(orders)) {
            Arrays.stream(orders).forEach(order -> {
                String[] arr = OrderUtils.parse(order);
                if (arr != null && ArrayUtils.isNotEmpty(arr)) {
                    if (OrderUtils.isAsc(arr[1])) {
                        orderList.add(Sort.Order.asc(arr[0]));
                    } else {
                        orderList.add(Sort.Order.desc(arr[1]));
                    }
                }
            });
        }
        return PageRequest.of(page, size, Sort.by(orderList));
    }

    private List<SortBuilder<?>> parseSortBuilders(QueryParams<T> params) {
        List<SortBuilder<?>> builders = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(params.getOrders())) {
            params.getOrders().forEach(order -> {
                String[] arr = OrderUtils.parse(order);
                if (arr != null && ArrayUtils.isNotEmpty(arr)) {
                    String name = arr[0];
                    if (OrderUtils.isAsc(arr[1])) {
                        if (StringUtils.equals(SCORE, name)) {
                            builders.add(new ScoreSortBuilder().order(SortOrder.ASC));
                        } else {
                            builders.add(new FieldSortBuilder(name).order(SortOrder.ASC));
                        }
                    } else {
                        if (StringUtils.equals(SCORE, name)) {
                            builders.add(new ScoreSortBuilder().order(SortOrder.DESC));
                        } else {
                            builders.add(new FieldSortBuilder(name).order(SortOrder.DESC));
                        }
                    }
                }
            });
        }
        return builders;
    }

    private QueryBuilder parseQueryBuilder(QueryParams<T> params, boolean parseModel) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // 处理实体类的属性
        T model = params.getModel();
        if (parseModel && model != null) {
            parseModelProps(builder, model);
        }
        // 处理QueryParams的属性
        parseParamsProps(builder, params);
        // 递归
        if (CollectionUtils.isNotEmpty(params.getAnds())) {
            params.getAnds().forEach(queryParams -> builder.must(parseQueryBuilder(params, false)));
        }
        if (CollectionUtils.isNotEmpty(params.getOrs())) {
            params.getOrs().forEach(queryParams -> builder.should(parseQueryBuilder(params, false)));
        }
        return builder;
    }

    private void parseModelProps(BoolQueryBuilder builder, T model) {
        java.lang.reflect.Field[] fields = ReflectUtils.getDeclaredFields(clazz);
        Arrays.stream(fields).forEach(field -> {
            Object value = ReflectUtils.getProp(model, field);
            if (value == null) {
                return;
            }
            Object parsedValue = parseObject(value);
            Field esField = field.getDeclaredAnnotation(Field.class);
            if (esField == null) {
                return;
            }
            String name = field.getName();
            switch (esField.type()) {
                case Integer:
                case Long:
                case Float:
                case Double:
                case Boolean:
                case Keyword:
                case Ip:
                case Date:
                    builder.must(new TermQueryBuilder(name, parsedValue));
                    break;
                case Auto:
                    Class<?> type = field.getType();
                    if (TypeUtils.isSimpleValueType(type)) {
                        builder.must(new TermQueryBuilder(name, parsedValue));
                    } else {
                        builder.must(new MatchQueryBuilder(name, parsedValue));
                    }
                    break;
                default:
                    builder.must(new MatchQueryBuilder(name, parsedValue));
                    break;
            }
        });
    }

    private void parseParamsProps(BoolQueryBuilder builder, QueryParams<T> params) {
        if (MapUtils.isNotEmpty(params.getEqMap())) {
            params.getEqMap().forEach((name, value) -> builder.must(new TermQueryBuilder(name, value)));
        }
        if (MapUtils.isNotEmpty(params.getNeMap())) {
            params.getNeMap().forEach((name, value) -> builder.mustNot(new TermQueryBuilder(name, value)));
        }
        if (MapUtils.isNotEmpty(params.getLtMap())) {
            params.getLtMap().forEach((name, value) -> builder.must(new RangeQueryBuilder(name).lt(value)));
        }
        if (MapUtils.isNotEmpty(params.getLeMap())) {
            params.getLeMap().forEach((name, value) -> builder.must(new RangeQueryBuilder(name).lte(value)));
        }
        if (MapUtils.isNotEmpty(params.getGtMap())) {
            params.getGtMap().forEach((name, value) -> builder.must(new RangeQueryBuilder(name).gt(value)));
        }
        if (MapUtils.isNotEmpty(params.getGeMap())) {
            params.getGeMap().forEach((name, value) -> builder.must(new RangeQueryBuilder(name).gte(value)));
        }
        if (MapUtils.isNotEmpty(params.getLikeMap())) {
            params.getLikeMap().forEach((name, value) -> builder.must(new MatchQueryBuilder(name, value)));
        }
        if (MapUtils.isNotEmpty(params.getNotLikeMap())) {
            params.getNotLikeMap().forEach((name, value) -> builder.mustNot(new MatchQueryBuilder(name, value)));
        }
        if (MapUtils.isNotEmpty(params.getInMap())) {
            params.getInMap().forEach((name, collection) -> {
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                collection.forEach(value -> boolQueryBuilder.should(new TermQueryBuilder(name, value)));
                builder.must(boolQueryBuilder);
            });
        }
        if (MapUtils.isNotEmpty(params.getNotInMap())) {
            params.getNotInMap().forEach((name, collection) -> {
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                collection.forEach(value -> boolQueryBuilder.mustNot(new TermQueryBuilder(name, value)));
                builder.must(boolQueryBuilder);
            });
        }
        if (CollectionUtils.isNotEmpty(params.getNulls())) {
            params.getNulls().forEach(name -> builder.mustNot(new ExistsQueryBuilder(name)));
        }
        if (CollectionUtils.isNotEmpty(params.getNotNulls())) {
            params.getNotNulls().forEach(name -> builder.must(new ExistsQueryBuilder(name)));
        }
    }

    private String randomString() {
        return UUID.randomUUID().toString();
    }
}
