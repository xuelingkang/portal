package com.xzixi.self.portal.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzixi.self.portal.webapp.exception.ProjectException;
import com.xzixi.self.portal.webapp.mapper.IBaseMapper;
import com.xzixi.self.portal.webapp.model.BaseModel;
import com.xzixi.self.portal.webapp.service.IBaseService;

import java.util.Map;

/**
 * 用来扩展公共的service方法
 * 禁用不根据id做更新和删除操作的方法，以便更好控制缓存
 *
 * @author 薛凌康
 */
public class BaseServiceImpl<M extends IBaseMapper<T>, T extends BaseModel> extends ServiceImpl<M, T> implements IBaseService<T> {

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
    public final boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper) {
        throw new ProjectException("禁止调用！");
    }
}
