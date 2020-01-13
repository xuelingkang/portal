package com.xzixi.self.portal.framework.data.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.enums.WrapperKeyword;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzixi.self.portal.framework.data.IBaseData;
import com.xzixi.self.portal.framework.mapper.IBaseMapper;
import com.xzixi.self.portal.framework.model.BaseModel;
import com.xzixi.self.portal.framework.model.search.Pagination;
import com.xzixi.self.portal.framework.model.search.QueryParams;
import com.xzixi.self.portal.framework.util.OrderUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
        return super.getOne(buildQueryWrapper(params), true);
    }

    @Override
    public List<T> list() {
        return list(new QueryParams<>());
    }

    @Override
    public List<T> list(QueryParams<T> params) {
        return super.list(buildQueryWrapper(params));
    }

    @Override
    public Pagination<T> page(Pagination<T> pagination, QueryParams<T> params) {
        IPage<T> page = super.page(buildPage(pagination), buildQueryWrapper(params));
        pagination.setCurrent(page.getCurrent());
        pagination.setSize(page.getSize());
        pagination.setTotal(page.getTotal());
        pagination.setRecords(page.getRecords());
        return pagination;
    }

    @Override
    public int count() {
        return count(new QueryParams<>());
    }

    @Override
    public int count(QueryParams<T> params) {
        return super.count(buildQueryWrapper(params));
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

    public Page<T> buildPage(Pagination<T> pagination) {
        Page<T> page = new Page<>(pagination.getCurrent(), pagination.getSize(), pagination.getTotal(), pagination.isSearchCount());
        String[] orders = pagination.getOrders();
        if (orders != null && ArrayUtils.isNotEmpty(orders)) {
            List<OrderItem> orderItems = new ArrayList<>();
            Arrays.stream(orders).forEach(order -> {
                String[] arr = OrderUtil.parse(order);
                if (arr != null && ArrayUtils.isNotEmpty(arr)) {
                    if (OrderUtil.isAsc(arr[1])) {
                        orderItems.add(OrderItem.asc(arr[0]));
                    } else {
                        orderItems.add(OrderItem.desc(arr[0]));
                    }
                }
            });
            page.setOrders(orderItems);
        }
        return page;
    }

    public QueryWrapper<T> buildQueryWrapper(QueryParams<T> params) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (params.getModel() != null) {
            queryWrapper.setEntity(params.getModel());
        }
        String[] columns = params.getColumns();
        if (columns != null && ArrayUtils.isNotEmpty(columns)) {
            queryWrapper.select(params.getColumns());
        }
        if (MapUtils.isNotEmpty(params.getEqMap())) {
            params.getEqMap().forEach(queryWrapper::eq);
        }
        if (MapUtils.isNotEmpty(params.getNeMap())) {
            params.getNeMap().forEach(queryWrapper::ne);
        }
        if (MapUtils.isNotEmpty(params.getLtMap())) {
            params.getLtMap().forEach(queryWrapper::lt);
        }
        if (MapUtils.isNotEmpty(params.getLeMap())) {
            params.getLeMap().forEach(queryWrapper::le);
        }
        if (MapUtils.isNotEmpty(params.getGtMap())) {
            params.getGtMap().forEach(queryWrapper::gt);
        }
        if (MapUtils.isNotEmpty(params.getGeMap())) {
            params.getGeMap().forEach(queryWrapper::ge);
        }
        if (MapUtils.isNotEmpty(params.getLikeMap())) {
            params.getLikeMap().forEach(queryWrapper::like);
        }
        if (MapUtils.isNotEmpty(params.getNotLikeMap())) {
            params.getNotLikeMap().forEach(queryWrapper::notLike);
        }
        if (MapUtils.isNotEmpty(params.getInMap())) {
            params.getInMap().forEach(queryWrapper::in);
        }
        if (MapUtils.isNotEmpty(params.getNotInMap())) {
            params.getNotInMap().forEach(queryWrapper::notIn);
        }
        if (CollectionUtils.isNotEmpty(params.getNulls())) {
            params.getNulls().forEach(queryWrapper::isNull);
        }
        if (CollectionUtils.isNotEmpty(params.getNotNulls())) {
            params.getNotNulls().forEach(queryWrapper::isNotNull);
        }
        if (CollectionUtils.isNotEmpty(params.getOrders())) {
            params.getOrders().forEach(order -> {
                String[] arr = OrderUtil.parse(order);
                if (arr != null && ArrayUtils.isNotEmpty(arr)) {
                    if (OrderUtil.isAsc(arr[1])) {
                        queryWrapper.orderByAsc(arr[0]);
                    } else {
                        queryWrapper.orderByDesc(arr[0]);
                    }
                }
            });
        }
        if (CollectionUtils.isNotEmpty(params.getAnds())) {
            params.getAnds().forEach(queryParams -> queryWrapper.getExpression()
                    .add(SqlKeyword.AND, WrapperKeyword.LEFT_BRACKET, buildQueryWrapper(queryParams), WrapperKeyword.RIGHT_BRACKET));
        }
        if (CollectionUtils.isNotEmpty(params.getOrs())) {
            params.getOrs().forEach(queryParams -> queryWrapper.getExpression()
                    .add(SqlKeyword.OR, WrapperKeyword.LEFT_BRACKET, buildQueryWrapper(queryParams), WrapperKeyword.RIGHT_BRACKET));
        }
        return queryWrapper;
    }
}
