package com.xzixi.self.portal.framework.data.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzixi.self.portal.framework.data.IBaseData;
import com.xzixi.self.portal.framework.mapper.IBaseMapper;
import com.xzixi.self.portal.framework.model.BaseModel;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 适配ElasticsearchTemplate
 *
 * @author 薛凌康
 */
public class ElasticsearchDataImpl<M extends IBaseMapper<T>, T extends BaseModel> extends AbstractDataImpl<M, T> implements IBaseData<T> {

    @Override
    public boolean save(T entity) {
        return false;
    }

    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean removeById(Serializable id) {
        return false;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return false;
    }

    @Override
    public boolean updateById(T entity) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        return false;
    }

    @Override
    public T getById(Serializable id) {
        return null;
    }

    @Override
    public Collection<T> listByIds(Collection<? extends Serializable> idList) {
        return null;
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
}
