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
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * elasticsearch实现
 *
 * @author 薛凌康
 */
public class ElasticsearchDataImpl<M extends IBaseMapper<T>, T extends BaseModel> extends ServiceImpl<M, T>
        implements IBaseData<T>, ISearchEngine {

    private Class<T> clazz;
    private String type;
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
        // Document注解的type属性
        this.type = document.type();
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
        // TODO
        return null;
    }

    @Override
    public Pagination<T> page(Pagination<T> pagination, QueryParams<T> params) {
        // TODO
        return null;
    }

    @Override
    public int count(QueryParams<T> params) {
        // TODO
        return 0;
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
        String node = randomNode();
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
        remove(QueryBuilders.idsQuery(type).addIds(String.valueOf(id)));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        // 检查是否正在进行初始化或同步
        checkInit();
        checkSync();
        String node = randomNode();
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
        remove(QueryBuilders.idsQuery(type).addIds(ids));
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
        String node = randomNode();
        try {
            // 挂载节点
            getInitLock().mount(node);
            // 二次检查
            checkInit();
            checkSync();
            // 注册监听
            getRemoveLock().register(this::doInit);
        } catch (Exception e) {
            throw new ServerException(null, "执行初始化失败！", e);
        } finally {
            getInitLock().unmount(node);
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
        int size = 1000;
        int current = 1;
        while (true) {
            IPage<T> page = super.page(new Page<T>(current, size).addOrder(OrderItem.asc("id")));
            List<T> list = page.getRecords();
            index(list, size);
            // 已经导入的数据个数
            int imported = (current - 1) * size + list.size();
            if (imported >= page.getTotal()) {
                break;
            }
            current++;
        }
    }

    @Override
    public void sync() {
        // 同步操作不等待初始化操作，冲突就返回异常，如果有未完成的删除操作就等待
        // 检查是否正在进行初始化
        checkInit();
        checkSync();
        String node = randomNode();
        try {
            // 挂载节点
            getSyncLock().mount(node);
            // 二次检查
            checkInit();
            checkSync();
            // 注册监听
            getRemoveLock().register(this::doSync);
        } catch (Exception e) {
            throw new ServerException(null, "执行同步失败！", e);
        } finally {
            getSyncLock().unmount(node);
        }
    }

    private void doSync() {
        // TODO 批量比对数据库和搜索引擎中的数据
    }

    /**
     * 检查是否正在进行初始化
     */
    private void checkInit() {
        if (getInitLock().isNotEmpty()) {
            throw new ServerException("搜索引擎正在进行初始化，请稍后再试！");
        }
    }

    /**
     * 检查是否正在进行同步
     */
    private void checkSync() {
        if (getSyncLock().isNotEmpty()) {
            throw new ServerException("搜索引擎正在进行同步，请稍后再试！");
        }
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

    private String randomNode() {
        return UUID.randomUUID().toString();
    }
}
