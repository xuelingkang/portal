package com.xzixi.self.portal.webapp.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzixi.self.portal.webapp.framework.data.IBaseData;
import com.xzixi.self.portal.webapp.framework.model.BaseModel;
import com.xzixi.self.portal.webapp.framework.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 代理baseData的所有方法
 *
 * @author 薛凌康
 */
public class BaseServiceImpl<T extends BaseModel, D extends IBaseData<T>> implements IBaseService<T> {

    @Autowired
    protected D baseData;

    @Override
    public T getById(Serializable id) {
        return baseData.getById(id);
    }

    @Override
    public Collection<T> listByIds(Collection<? extends Serializable> idList) {
        return baseData.listByIds(idList);
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
        return baseData.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        return baseData.updateBatchById(entityList, batchSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(T entity) {
        return baseData.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        return baseData.saveBatch(entityList, batchSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(T entity) {
        return baseData.saveOrUpdate(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        return baseData.saveOrUpdateBatch(entityList, batchSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        return baseData.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<? extends Serializable> idList) {
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
