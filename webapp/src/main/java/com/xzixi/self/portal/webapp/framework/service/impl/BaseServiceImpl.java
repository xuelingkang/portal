package com.xzixi.self.portal.webapp.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzixi.self.portal.webapp.framework.data.IBaseData;
import com.xzixi.self.portal.webapp.framework.exception.LogicException;
import com.xzixi.self.portal.webapp.framework.exception.ProjectException;
import com.xzixi.self.portal.webapp.framework.model.BaseModel;
import com.xzixi.self.portal.webapp.framework.service.IBaseService;
import com.xzixi.self.portal.webapp.framework.util.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 代理baseData的所有方法
 *
 * @author 薛凌康
 */
public class BaseServiceImpl<T extends BaseModel, D extends IBaseData<T>> implements IBaseService<T> {

    @Autowired
    private D baseData;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByIdIgnoreNullProps(T entity) {
        T entityData = getById(entity.getId());
        BeanUtils.copyPropertiesIgnoreNull(entity, entityData);
        return updateById(entityData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchByIdIgnoreNullProps(Collection<T> entityList, int batchSize) {
        Collection<Integer> idList = entityList.stream().map(BaseModel::getId).collect(Collectors.toList());
        Collection<T> entities = listByIds(idList);
        entities.forEach(entityData -> {
            Optional<T> entityOptional = entityList.stream()
                .filter(entity -> entityData.getId().equals(entity.getId())).findFirst();
            entityOptional.ifPresent(entity -> BeanUtils.copyPropertiesIgnoreNull(entity, entityData));
        });
        return updateBatchById(entities, batchSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void merge(Collection<T> newModels, Collection<T> oldModels, MergeComparator<T> comparator) {
        // 需要保存的数据
        List<T> modelsForSave = newModels.stream()
                .filter(newModel -> comparator.find(oldModels, newModel) == null)
                .collect(Collectors.toList());
        // 需要删除的数据id
        List<Integer> modelIdsForRemove = oldModels.stream()
                .filter(oldModel -> comparator.find(newModels, oldModel) == null)
                .map(T::getId)
                .collect(Collectors.toList());
        // 需要更新的数据
        List<T> modelsForUpdate = oldModels.stream()
                .filter(oldModel -> {
                    T newModel = comparator.find(newModels, oldModel);
                    if (newModel == null) {
                        return false;
                    }
                    BeanUtils.copyPropertiesIgnoreNull(newModel, oldModel);
                    return true;
                })
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(modelsForSave)) {
            saveBatch(modelsForSave);
        }
        if (CollectionUtils.isNotEmpty(modelIdsForRemove)) {
            removeByIds(modelIdsForRemove);
        }
        if (CollectionUtils.isNotEmpty(modelsForUpdate)) {
            updateBatchById(modelsForUpdate);
        }
    }

    @Override
    public T getById(Serializable id) {
        T entity = baseData.getById(id);
        if (entity == null) {
            throw new LogicException(404, String.format("id为%s的记录不存在！", id));
        }
        return entity;
    }

    @Override
    public Collection<T> listByIds(Collection<? extends Serializable> idList) {
        return baseData.listByIds(idList);
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper) {
        T entity = getOne(queryWrapper, false);
        if (entity == null) {
            throw new LogicException(404, "记录不存在！");
        }
        return entity;
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        return baseData.getOne(queryWrapper, throwEx);
    }

    @Override
    public List<T> list(Wrapper<T> queryWrapper) {
        return baseData.list(queryWrapper);
    }

    @Override
    public Collection<T> listByMap(Map<String, Object> columnMap) {
        return baseData.listByMap(columnMap);
    }

    @Override
    public IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper) {
        return baseData.page(page, queryWrapper);
    }

    @Override
    public int count(Wrapper<T> queryWrapper) {
        return baseData.count(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(T entity) {
        if (entity.getId() == null) {
            throw new ProjectException("id不能为null！");
        }
        return baseData.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        for (T entity : entityList) {
            if (entity.getId() == null) {
                throw new ProjectException("id不能为null！");
            }
        }
        return baseData.updateBatchById(entityList, batchSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(T entity) {
        if (entity == null) {
            throw new ProjectException("entity不能为null！");
        }
        return baseData.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new ProjectException("entityList不能为空！");
        }
        return baseData.saveBatch(entityList, batchSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(T entity) {
        if (entity == null) {
            throw new ProjectException("entity不能为null！");
        }
        return baseData.saveOrUpdate(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new ProjectException("entityList不能为空！");
        }
        return baseData.saveOrUpdateBatch(entityList, batchSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        if (id == null) {
            throw new ProjectException("id不能为null！");
        }
        return baseData.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        idList = idList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(idList)) {
            throw new ProjectException("idList不能为空！");
        }
        return baseData.removeByIds(idList);
    }

    @Override
    public final Map<String, Object> getMap(Wrapper<T> queryWrapper) {
        return baseData.getMap(queryWrapper);
    }

    @Override
    public final <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return baseData.getObj(queryWrapper, mapper);
    }

    @Override
    public final List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
        return baseData.listMaps(queryWrapper);
    }

    @Override
    public final <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return baseData.listObjs(queryWrapper, mapper);
    }

    @Override
    public final IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper) {
        return baseData.pageMaps(page, queryWrapper);
    }

    @Override
    public final BaseMapper<T> getBaseMapper() {
        return baseData.getBaseMapper();
    }

    @Override
    public final boolean update(T entity, Wrapper<T> updateWrapper) {
        return baseData.update(entity, updateWrapper);
    }

    @Override
    public final boolean removeByMap(Map<String, Object> columnMap) {
        return baseData.removeByMap(columnMap);
    }

    @Override
    public final boolean remove(Wrapper<T> queryWrapper) {
        return baseData.remove(queryWrapper);
    }
}
