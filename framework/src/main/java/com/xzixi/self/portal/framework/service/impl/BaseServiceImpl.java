package com.xzixi.self.portal.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzixi.self.portal.framework.data.IBaseData;
import com.xzixi.self.portal.framework.exception.ClientException;
import com.xzixi.self.portal.framework.exception.ProjectException;
import com.xzixi.self.portal.framework.model.BaseModel;
import com.xzixi.self.portal.framework.service.IBaseService;
import com.xzixi.self.portal.framework.util.BeanUtils;
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
public class BaseServiceImpl<D extends IBaseData<T>, T extends BaseModel> implements IBaseService<T> {

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
                .filter(entity -> Objects.equals(entityData.getId(), entity.getId())).findFirst();
            entityOptional.ifPresent(entity -> BeanUtils.copyPropertiesIgnoreNull(entity, entityData));
        });
        return updateBatchById(entities, batchSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean merge(Collection<T> newModels, Collection<T> oldModels, MergeSelector<T> selector) {
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
            if (!saveBatch(modelsForSave)) {
                return false;
            }
        }
        if (CollectionUtils.isNotEmpty(modelIdsForRemove)) {
            if (!removeByIds(modelIdsForRemove)) {
                return false;
            }
        }
        if (CollectionUtils.isNotEmpty(modelsForUpdate)) {
            return updateBatchById(modelsForUpdate);
        }
        return true;
    }

    @Override
    public T getById(Serializable id, boolean throwEx) {
        T entity = baseData.getById(id);
        if (entity == null && throwEx) {
            throw new ClientException(404, String.format("id为%s的记录不存在！", id));
        }
        return entity;
    }

    @Override
    public T getById(Serializable id) {
        return getById(id, true);
    }

    @Override
    public Collection<T> listByIds(Collection<? extends Serializable> idList) {
        idList = idList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(idList)) {
            throw new ProjectException("idList不能为空！");
        }
        return baseData.listByIds(idList);
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper) {
        T entity = getOne(queryWrapper, false);
        if (entity == null) {
            throw new ClientException(404, "记录不存在！");
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
        idList = idList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
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
