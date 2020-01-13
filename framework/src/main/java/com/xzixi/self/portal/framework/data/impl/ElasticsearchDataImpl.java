package com.xzixi.self.portal.framework.data.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzixi.self.portal.framework.data.IBaseData;
import com.xzixi.self.portal.framework.exception.ProjectException;
import com.xzixi.self.portal.framework.exception.ServerException;
import com.xzixi.self.portal.framework.mapper.IBaseMapper;
import com.xzixi.self.portal.framework.model.BaseModel;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 适配ElasticsearchTemplate
 *
 * @author 薛凌康
 */
public class ElasticsearchDataImpl<M extends IBaseMapper<T>, T extends BaseModel> extends AbstractDataImpl<M, T> implements IBaseData<T> {

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
        if (StringUtils.isBlank(type)) {
            throw new ProjectException(String.format("类(%s)必须设置@Document注解的type属性", clazz.getName()));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(T entity) {
        if (!super.save(entity)) {
            throw new ServerException(entity, "数据库写入失败！");
        }
        index(entity);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        if (!super.saveBatch(entityList, batchSize)) {
            throw new ServerException(entityList, "数据库写入失败！");
        }
        index(entityList, batchSize);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        if (!super.saveOrUpdateBatch(entityList, batchSize)) {
            throw new ServerException(entityList, "数据库写入失败！");
        }
        index(entityList, batchSize);
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
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(T entity) {
        if (!super.updateById(entity)) {
            throw new ServerException(entity, "数据库写入失败！");
        }
        index(entity);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        if (!super.updateBatchById(entityList, batchSize)) {
            throw new ServerException(entityList, "数据库写入失败！");
        }
        index(entityList, batchSize);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(T entity) {
        if (!super.saveOrUpdate(entity)) {
            throw new ServerException(entity, "数据库写入失败！");
        }
        index(entity);
        return true;
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
    public Collection<T> listByMap(Map<String, Object> columnMap) {
        return null;
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public int count(Wrapper<T> queryWrapper) {
        return 0;
    }

    @Override
    public List<T> list(Wrapper<T> queryWrapper) {
        return null;
    }

    @Override
    public IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper) {
        return null;
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
            while (toIndex < entities.size()) {
                List<T> subList = entities.subList(fromIndex, toIndex);
                List<IndexQuery> queries = subList.stream()
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
}
