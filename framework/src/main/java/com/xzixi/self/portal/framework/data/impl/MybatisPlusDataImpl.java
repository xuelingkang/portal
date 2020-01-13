package com.xzixi.self.portal.framework.data.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzixi.self.portal.framework.data.IBaseData;
import com.xzixi.self.portal.framework.mapper.IBaseMapper;
import com.xzixi.self.portal.framework.model.BaseModel;
import com.xzixi.self.portal.framework.model.search.Pagination;
import com.xzixi.self.portal.framework.model.search.QueryParams;

import java.util.Collection;
import java.util.List;

/**
 * mybatis-plus实现
 *
 * @author 薛凌康
 */
public class MybatisPlusDataImpl<M extends IBaseMapper<T>, T extends BaseModel> extends ServiceImpl<M, T> implements IBaseData<T> {

    @Override
    public T getOne(QueryParams<T> params) {
        return null;
    }

    @Override
    public List<T> list(QueryParams<T> params) {
        return null;
    }

    @Override
    public Pagination<T> page(Pagination<T> pagination, QueryParams<T> params) {
        return null;
    }

    @Override
    public int count(QueryParams<T> params) {
        return 0;
    }

    @Override
    public boolean saveBatch(Collection<T> models) {
        return saveBatch(models, 1000);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> models) {
        return saveOrUpdateBatch(models, 1000);
    }

    @Override
    public boolean updateBatchById(Collection<T> models) {
        return updateBatchById(models, 1000);
    }

    @Override
    public int count() {
        return count(new QueryParams<>());
    }
}
