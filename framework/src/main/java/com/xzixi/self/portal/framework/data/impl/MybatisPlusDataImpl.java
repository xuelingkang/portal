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
import com.xzixi.self.portal.framework.util.HumpNameUtil;
import com.xzixi.self.portal.framework.util.OrderUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.xzixi.self.portal.framework.util.HumpNameUtil.humpToLine;

/**
 * mybatis-plus实现
 *
 * @author 薛凌康
 */
public class MybatisPlusDataImpl<M extends IBaseMapper<T>, T extends BaseModel> extends ServiceImpl<M, T> implements IBaseData<T> {

    @Override
    public T getOne(QueryParams<T> params) {
        return super.getOne(parseQueryWrapper(params), true);
    }

    @Override
    public List<T> list() {
        return list(new QueryParams<>());
    }

    @Override
    public List<T> list(QueryParams<T> params) {
        return super.list(parseQueryWrapper(params));
    }

    @Override
    public Pagination<T> page(Pagination<T> pagination, QueryParams<T> params) {
        IPage<T> page = super.page(parsePage(pagination), parseQueryWrapper(params));
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
        return super.count(parseQueryWrapper(params));
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

    private Page<T> parsePage(Pagination<T> pagination) {
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

    private QueryWrapper<T> parseQueryWrapper(QueryParams<T> params) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (params.getModel() != null) {
            queryWrapper.setEntity(params.getModel());
        }
        String[] columns = params.getColumns();
        if (columns != null && ArrayUtils.isNotEmpty(columns)) {
            columns = Arrays.stream(columns).map(HumpNameUtil::humpToLine).toArray(String[]::new);
            queryWrapper.select(columns);
        }
        if (MapUtils.isNotEmpty(params.getEqMap())) {
            params.getEqMap().forEach((name, value) -> queryWrapper.eq(humpToLine(name), value));
        }
        if (MapUtils.isNotEmpty(params.getNeMap())) {
            params.getNeMap().forEach((name, value) -> queryWrapper.ne(humpToLine(name), value));
        }
        if (MapUtils.isNotEmpty(params.getLtMap())) {
            params.getLtMap().forEach((name, value) -> queryWrapper.lt(humpToLine(name), value));
        }
        if (MapUtils.isNotEmpty(params.getLeMap())) {
            params.getLeMap().forEach((name, value) -> queryWrapper.le(humpToLine(name), value));
        }
        if (MapUtils.isNotEmpty(params.getGtMap())) {
            params.getGtMap().forEach((name, value) -> queryWrapper.gt(humpToLine(name), value));
        }
        if (MapUtils.isNotEmpty(params.getGeMap())) {
            params.getGeMap().forEach((name, value) -> queryWrapper.ge(humpToLine(name), value));
        }
        if (MapUtils.isNotEmpty(params.getLikeMap())) {
            params.getLikeMap().forEach((name, value) -> queryWrapper.like(humpToLine(name), value));
        }
        if (MapUtils.isNotEmpty(params.getNotLikeMap())) {
            params.getNotLikeMap().forEach((name, value) -> queryWrapper.notLike(humpToLine(name), value));
        }
        if (MapUtils.isNotEmpty(params.getInMap())) {
            params.getInMap().forEach((name, value) -> queryWrapper.in(humpToLine(name), value));
        }
        if (MapUtils.isNotEmpty(params.getNotInMap())) {
            params.getNotInMap().forEach((name, value) -> queryWrapper.notIn(humpToLine(name), value));
        }
        if (CollectionUtils.isNotEmpty(params.getNulls())) {
            params.getNulls().forEach(name -> queryWrapper.isNull(humpToLine(name)));
        }
        if (CollectionUtils.isNotEmpty(params.getNotNulls())) {
            params.getNotNulls().forEach(name -> queryWrapper.isNotNull(humpToLine(name)));
        }
        if (CollectionUtils.isNotEmpty(params.getOrders())) {
            params.getOrders().forEach(order -> {
                String[] arr = OrderUtil.parse(order);
                if (arr != null && ArrayUtils.isNotEmpty(arr)) {
                    if (OrderUtil.isAsc(arr[1])) {
                        queryWrapper.orderByAsc(humpToLine(arr[0]));
                    } else {
                        queryWrapper.orderByDesc(humpToLine(arr[0]));
                    }
                }
            });
        }
        if (CollectionUtils.isNotEmpty(params.getAnds())) {
            params.getAnds().forEach(queryParams -> queryWrapper.getExpression()
                    .add(SqlKeyword.AND, WrapperKeyword.LEFT_BRACKET, parseQueryWrapper(queryParams), WrapperKeyword.RIGHT_BRACKET));
        }
        if (CollectionUtils.isNotEmpty(params.getOrs())) {
            params.getOrs().forEach(queryParams -> queryWrapper.getExpression()
                    .add(SqlKeyword.OR, WrapperKeyword.LEFT_BRACKET, parseQueryWrapper(queryParams), WrapperKeyword.RIGHT_BRACKET));
        }
        return queryWrapper;
    }
}
