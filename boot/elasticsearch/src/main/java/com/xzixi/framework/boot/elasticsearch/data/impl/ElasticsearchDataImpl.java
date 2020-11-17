/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.boot.elasticsearch.data.impl;

import com.xzixi.framework.boot.persistent.data.IBaseData;
import com.xzixi.framework.boot.core.exception.ProjectException;
import com.xzixi.framework.boot.core.exception.ServerException;
import com.xzixi.framework.boot.core.model.BaseModel;
import com.xzixi.framework.boot.core.model.search.Pagination;
import com.xzixi.framework.boot.core.model.search.QueryParams;
import com.xzixi.framework.boot.core.util.OrderUtils;
import com.xzixi.framework.boot.core.util.ReflectUtils;
import com.xzixi.framework.boot.core.util.TypeUtils;
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

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.xzixi.framework.boot.core.model.search.QueryParams.SCORE;
import static com.xzixi.framework.boot.core.util.TypeUtils.parseObject;

/**
 * elasticsearch实现
 *
 * @author 薛凌康
 */
public class ElasticsearchDataImpl<T extends BaseModel> implements IBaseData<T> {

    private final Class<T> clazz;
    @Autowired
    protected ElasticsearchTemplate elasticsearchTemplate;

    @SuppressWarnings("unchecked")
    public ElasticsearchDataImpl() {
        // 获取T的实际类型，第二个泛型参数
        this.clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        // 获取Document注解
        Document document = clazz.getDeclaredAnnotation(Document.class);
        if (document == null) {
            throw new ProjectException(String.format("类(%s)必须使用@Document注解！", clazz.getName()));
        }
    }

    @Override
    public T getById(Serializable id) {
        GetQuery getQuery = new GetQuery();
        getQuery.setId(String.valueOf(id));
        return elasticsearchTemplate.queryForObject(getQuery, clazz);
    }

    @Override
    public List<T> listByIds(Collection<? extends Serializable> idList) {
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
    public boolean save(T model) {
        index(model);
        return true;
    }

    @Override
    public boolean saveBatch(Collection<T> models, int batchSize) {
        index(models, batchSize);
        return true;
    }

    @Override
    public boolean saveOrUpdate(T model) {
        index(model);
        return true;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> models, int batchSize) {
        index(models, batchSize);
        return true;
    }

    @Override
    public boolean updateById(T model) {
        index(model);
        return true;
    }

    @Override
    public boolean updateBatchById(Collection<T> models, int batchSize) {
        index(models, batchSize);
        return true;
    }

    @Override
    public boolean removeById(Serializable id) {
        remove(QueryBuilders.idsQuery().addIds(String.valueOf(id)));
        return true;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        String[] ids = idList.stream().map(String::valueOf).toArray(String[]::new);
        remove(QueryBuilders.idsQuery().addIds(ids));
        return true;
    }

    @Override
    public int defaultBatchSize() {
        return 1000;
    }

    private void index(T entity) {
        try {
            IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(String.valueOf(entity.getId()))
                .withObject(entity)
                .build();
            elasticsearchTemplate.index(indexQuery);
        } catch (Exception e) {
            throw new ServerException(entity, "索引失败！", e);
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
            throw new ServerException(entityList, "索引失败！", e);
        }
    }

    private void remove(QueryBuilder queryBuilder) {
        try {
            DeleteQuery deleteQuery = new DeleteQuery();
            deleteQuery.setQuery(queryBuilder);
            elasticsearchTemplate.delete(deleteQuery, clazz);
        } catch (Exception e) {
            throw new ServerException(queryBuilder, "删除失败！", e);
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
}
