package com.xzixi.self.portal.framework.data.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzixi.self.portal.framework.data.IBaseData;
import com.xzixi.self.portal.framework.exception.ProjectException;
import com.xzixi.self.portal.framework.exception.ServerException;
import com.xzixi.self.portal.framework.mapper.IBaseMapper;
import com.xzixi.self.portal.framework.model.BaseModel;
import com.xzixi.self.portal.framework.model.search.Pagination;
import com.xzixi.self.portal.framework.model.search.QueryParams;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.stream.Collectors;

/**
 * elasticsearch实现
 *
 * @author 薛凌康
 */
public class ElasticsearchDataImpl<M extends IBaseMapper<T>, T extends BaseModel> extends ServiceImpl<M, T> implements IBaseData<T> {

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
        if (!super.removeById(id)) {
            throw new ServerException(id, "数据库写入失败！");
        }
        remove(QueryBuilders.idsQuery(type).addIds(String.valueOf(id)));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<? extends Serializable> idList) {
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
            List<IndexQuery> queries = null;
            int fromIndex = 0;
            int toIndex = fromIndex + batchSize;
            while (toIndex < entities.size()) {
                queries = entities.subList(fromIndex, toIndex).stream()
                    .map(entity -> new IndexQueryBuilder()
                        .withId(String.valueOf(entity.getId())).withObject(entity).build())
                    .collect(Collectors.toList());
                fromIndex = toIndex;
                toIndex += batchSize;
            }
            if (CollectionUtils.isNotEmpty(queries)) {
                elasticsearchTemplate.bulkIndex(queries);
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
}
