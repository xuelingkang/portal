package com.xzixi.self.portal.framework.service.impl;

import com.xzixi.self.portal.framework.data.IBaseData;
import com.xzixi.self.portal.framework.exception.ClientException;
import com.xzixi.self.portal.framework.exception.ProjectException;
import com.xzixi.self.portal.framework.model.BaseModel;
import com.xzixi.self.portal.framework.model.search.Pagination;
import com.xzixi.self.portal.framework.model.search.QueryParams;
import com.xzixi.self.portal.framework.service.IBaseService;
import com.xzixi.self.portal.framework.util.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 代理baseData的所有方法，扩展公共的service方法
 *
 * @author 薛凌康
 */
public class BaseServiceImpl<D extends IBaseData<T>, T extends BaseModel> implements IBaseService<T> {

    @Autowired
    protected D baseData;

    @Override
    public final boolean updateByIdIgnoreNullProps(T entity) {
        T entityData = getById(entity.getId());
        BeanUtils.copyPropertiesIgnoreNull(entity, entityData);
        return updateById(entityData);
    }

    @Override
    public final boolean updateBatchByIdIgnoreNullProps(Collection<T> entityList) {
        return updateBatchByIdIgnoreNullProps(entityList, 1000);
    }

    @Override
    public final boolean updateBatchByIdIgnoreNullProps(Collection<T> entityList, int batchSize) {
        Collection<Integer> idList = entityList.stream().map(BaseModel::getId).collect(Collectors.toList());
        Collection<T> entities = listByIds(idList);
        entities.forEach(entityData -> {
            entityList.stream()
                    .filter(entity -> Objects.equals(entityData.getId(), entity.getId())).findFirst()
                    .ifPresent(entity -> BeanUtils.copyPropertiesIgnoreNull(entity, entityData));
        });
        return updateBatchById(entities, batchSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public final boolean merge(Collection<T> newModels, Collection<T> oldModels, MergeSelector<T> selector) {
        // 需要保存的数据
        List<T> modelsForSave = newModels.stream()
                .filter(newModel -> selector.select(oldModels, newModel) == null)
                .collect(Collectors.toList());
        // 需要删除的数据id
        List<Integer> modelIdsForRemove = oldModels.stream()
                .filter(oldModel -> selector.select(newModels, oldModel) == null)
                .map(T::getId)
                .collect(Collectors.toList());
        // 需要更新的数据
        List<T> modelsForUpdate = oldModels.stream()
                .filter(oldModel -> {
                    T newModel = selector.select(newModels, oldModel);
                    if (newModel == null) {
                        return false;
                    }
                    BeanUtils.copyPropertiesIgnoreNull(newModel, oldModel);
                    return true;
                })
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(modelsForSave)) {
            if (!defaultSaveBatch(modelsForSave)) {
                return false;
            }
        }
        if (CollectionUtils.isNotEmpty(modelIdsForRemove)) {
            if (!removeByIds(modelIdsForRemove)) {
                return false;
            }
        }
        if (CollectionUtils.isNotEmpty(modelsForUpdate)) {
            return defaultUpdateBatchById(modelsForUpdate);
        }
        return true;
    }

    @Override
    public final T getById(Serializable id, boolean throwEx) {
        T entity = baseData.getById(id);
        if (entity == null && throwEx) {
            throw new ClientException(404, String.format("id为%s的记录不存在！", id));
        }
        return entity;
    }

    @Override
    public final T getById(Serializable id) {
        return getById(id, true);
    }

    @Override
    public final Collection<T> listByIds(Collection<? extends Serializable> idList) {
        idList = idList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(idList)) {
            throw new ProjectException("idList不能为空！");
        }
        return baseData.listByIds(idList);
    }

    @Override
    public final T getOne(QueryParams<T> params, boolean throwEx) {
        return baseData.getOne(params, throwEx);
    }

    @Override
    public final T getOne(QueryParams<T> params) {
        return baseData.getOne(params);
    }

    @Override
    public final List<T> listAll() {
        return baseData.listAll();
    }

    @Override
    public final List<T> list(QueryParams<T> params) {
        return baseData.list(params);
    }

    @Override
    public final Pagination<T> page(Pagination<T> pagination, QueryParams<T> params) {
        return baseData.page(pagination, params);
    }

    @Override
    public final int countAll() {
        return baseData.countAll();
    }

    @Override
    public final int count(QueryParams<T> params) {
        return baseData.count(params);
    }

    @Override
    public final boolean save(T entity) {
        if (entity == null) {
            throw new ProjectException("entity不能为null！");
        }
        return baseData.save(entity);
    }

    @Override
    public final boolean defaultSaveBatch(Collection<T> entityList) {
        return baseData.defaultSaveBatch(entityList);
    }

    @Override
    public final boolean saveBatch(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new ProjectException("entityList不能为空！");
        }
        return baseData.saveBatch(entityList, batchSize);
    }

    @Override
    public final boolean saveOrUpdate(T entity) {
        if (entity == null) {
            throw new ProjectException("entity不能为null！");
        }
        return baseData.saveOrUpdate(entity);
    }

    @Override
    public final boolean defaultSaveOrUpdateBatch(Collection<T> entityList) {
        return baseData.defaultSaveOrUpdateBatch(entityList);
    }

    @Override
    public final boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new ProjectException("entityList不能为空！");
        }
        return baseData.saveOrUpdateBatch(entityList, batchSize);
    }

    @Override
    public final boolean updateById(T entity) {
        if (entity.getId() == null) {
            throw new ProjectException("id不能为null！");
        }
        return baseData.updateById(entity);
    }

    @Override
    public final boolean defaultUpdateBatchById(Collection<T> entityList) {
        return baseData.defaultUpdateBatchById(entityList);
    }

    @Override
    public final boolean updateBatchById(Collection<T> entityList, int batchSize) {
        for (T entity : entityList) {
            if (entity.getId() == null) {
                throw new ProjectException("id不能为null！");
            }
        }
        return baseData.updateBatchById(entityList, batchSize);
    }

    @Override
    public final boolean removeById(Serializable id) {
        if (id == null) {
            throw new ProjectException("id不能为null！");
        }
        return baseData.removeById(id);
    }

    @Override
    public final boolean removeByIds(Collection<? extends Serializable> idList) {
        idList = idList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(idList)) {
            throw new ProjectException("idList不能为空！");
        }
        return baseData.removeByIds(idList);
    }

    @Override
    public final int defaultBatchSize() {
        return baseData.defaultBatchSize();
    }
}
