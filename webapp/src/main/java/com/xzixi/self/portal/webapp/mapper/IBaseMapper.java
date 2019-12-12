package com.xzixi.self.portal.webapp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzixi.self.portal.webapp.exception.ProjectException;
import com.xzixi.self.portal.webapp.model.BaseModel;

import java.util.List;
import java.util.Map;

/**
 * 用来扩展公共的mapper方法
 * 禁用不根据id做更新和删除操作的方法，以便更好控制缓存
 *
 * @author 薛凌康
 */
public interface IBaseMapper<T extends BaseModel> extends BaseMapper<T> {

    @Override
    default int deleteByMap(Map<String, Object> columnMap) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    default int delete(Wrapper<T> wrapper) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    default int update(T entity, Wrapper<T> updateWrapper) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    default List<Map<String, Object>> selectMaps(Wrapper<T> queryWrapper) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    default List<Object> selectObjs(Wrapper<T> queryWrapper) {
        throw new ProjectException("禁止调用！");
    }

    @Override
    default IPage<Map<String, Object>> selectMapsPage(IPage<T> page, Wrapper<T> queryWrapper) {
        throw new ProjectException("禁止调用！");
    }
}
