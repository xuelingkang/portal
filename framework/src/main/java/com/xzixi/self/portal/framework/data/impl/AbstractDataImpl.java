package com.xzixi.self.portal.framework.data.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzixi.self.portal.framework.data.IBaseData;
import com.xzixi.self.portal.framework.exception.ProjectException;
import com.xzixi.self.portal.framework.mapper.IBaseMapper;
import com.xzixi.self.portal.framework.model.BaseModel;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author 薛凌康
 */
public class AbstractDataImpl<M extends IBaseMapper<T>, T extends BaseModel> extends ServiceImpl<M, T> implements IBaseData<T> {

    @Override
    public final boolean removeByMap(Map<String, Object> columnMap) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final boolean remove(Wrapper<T> wrapper) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final boolean update(T entity, Wrapper<T> updateWrapper) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final boolean update(Wrapper<T> updateWrapper) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final Map<String, Object> getMap(Wrapper<T> queryWrapper) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final List<Map<String, Object>> listMaps() {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final List<Object> listObjs() {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final <V> List<V> listObjs(Function<? super Object, V> mapper) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final List<Object> listObjs(Wrapper<T> queryWrapper) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final IPage<Map<String, Object>> pageMaps(IPage<T> page) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final UpdateChainWrapper<T> update() {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final LambdaUpdateChainWrapper<T> lambdaUpdate() {
        throw new ProjectException("禁止调用！");
    }

    @Override
    public final M getBaseMapper() {
        throw new ProjectException("禁止调用！");
    }
}
