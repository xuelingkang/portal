/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.boot.mybatis.data.impl;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzixi.framework.boot.persistent.data.IBaseData;
import com.xzixi.framework.boot.mybatis.mapper.IBaseMapper;
import com.xzixi.framework.boot.core.model.BaseModel;
import com.xzixi.framework.boot.core.model.search.Pagination;
import com.xzixi.framework.boot.core.model.search.QueryParams;
import com.xzixi.framework.boot.core.util.OrderUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
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
    public T getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public List<T> listByIds(Collection<? extends Serializable> idList) {
        return super.listByIds(idList);
    }

    @Override
    public List<T> list(QueryParams<T> params) {
        return super.list(parseQueryWrapper(params, true, true));
    }

    @Override
    public Pagination<T> page(Pagination<T> pagination, QueryParams<T> params) {
        IPage<T> page = super.page(parsePage(pagination), parseQueryWrapper(params, true, true));
        pagination.setCurrent(page.getCurrent());
        pagination.setSize(page.getSize());
        pagination.setTotal(page.getTotal());
        pagination.setRecords(page.getRecords());
        return pagination;
    }

    @Override
    public long count(QueryParams<T> params) {
        return super.count(parseQueryWrapper(params, true, false));
    }

    @Override
    public boolean save(T entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(T entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @Override
    public int defaultBatchSize() {
        return 1000;
    }

    private Page<T> parsePage(Pagination<T> pagination) {
        Page<T> page = new Page<>(pagination.getCurrent(), pagination.getSize(), pagination.getTotal(), pagination.isSearchCount());
        String[] orders = pagination.getOrders();
        if (orders != null && ArrayUtils.isNotEmpty(orders)) {
            List<OrderItem> orderItems = new ArrayList<>();
            Arrays.stream(orders).forEach(order -> {
                String[] arr = OrderUtils.parse(order);
                if (arr != null && ArrayUtils.isNotEmpty(arr)) {
                    if (OrderUtils.isAsc(arr[1])) {
                        orderItems.add(OrderItem.asc(StringUtils.camelToUnderline(arr[0])));
                    } else {
                        orderItems.add(OrderItem.desc(StringUtils.camelToUnderline(arr[0])));
                    }
                }
            });
            page.setOrders(orderItems);
        }
        return page;
    }

    private QueryWrapper<T> parseQueryWrapper(QueryParams<T> params, boolean parseModel, boolean parseColumns) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (parseModel && params.getModel() != null) {
            queryWrapper.setEntity(params.getModel());
        }
        if (parseColumns) {
            String[] columns = params.getColumns();
            if (columns != null && ArrayUtils.isNotEmpty(columns)) {
                columns = Arrays.stream(columns).map(StringUtils::camelToUnderline).toArray(String[]::new);
                queryWrapper.select(columns);
            }
        }
        // 处理QueryParams的属性
        parseParamsProps(queryWrapper, params);
        // 递归
        if (CollectionUtils.isNotEmpty(params.getAnds())) {
            params.getAnds().forEach(queryParams -> queryWrapper.getExpression()
                    .add(SqlKeyword.AND, WrapperKeyword.LEFT_BRACKET, parseQueryWrapper(queryParams, false, false), WrapperKeyword.RIGHT_BRACKET));
        }
        if (CollectionUtils.isNotEmpty(params.getOrs())) {
            params.getOrs().forEach(queryParams -> queryWrapper.getExpression()
                    .add(SqlKeyword.OR, WrapperKeyword.LEFT_BRACKET, parseQueryWrapper(queryParams, false, false), WrapperKeyword.RIGHT_BRACKET));
        }
        return queryWrapper;
    }

    private void parseParamsProps(QueryWrapper<T> queryWrapper, QueryParams<T> params) {
        if (MapUtils.isNotEmpty(params.getEqMap())) {
            params.getEqMap().forEach((name, value) -> queryWrapper.eq(StringUtils.camelToUnderline(name), value));
        }
        if (MapUtils.isNotEmpty(params.getNeMap())) {
            params.getNeMap().forEach((name, value) -> queryWrapper.ne(StringUtils.camelToUnderline(name), value));
        }
        if (MapUtils.isNotEmpty(params.getLtMap())) {
            params.getLtMap().forEach((name, value) -> queryWrapper.lt(StringUtils.camelToUnderline(name), value));
        }
        if (MapUtils.isNotEmpty(params.getLeMap())) {
            params.getLeMap().forEach((name, value) -> queryWrapper.le(StringUtils.camelToUnderline(name), value));
        }
        if (MapUtils.isNotEmpty(params.getGtMap())) {
            params.getGtMap().forEach((name, value) -> queryWrapper.gt(StringUtils.camelToUnderline(name), value));
        }
        if (MapUtils.isNotEmpty(params.getGeMap())) {
            params.getGeMap().forEach((name, value) -> queryWrapper.ge(StringUtils.camelToUnderline(name), value));
        }
        if (MapUtils.isNotEmpty(params.getLikeMap())) {
            params.getLikeMap().forEach((name, value) -> queryWrapper.like(StringUtils.camelToUnderline(name), value));
        }
        if (MapUtils.isNotEmpty(params.getNotLikeMap())) {
            params.getNotLikeMap().forEach((name, value) -> queryWrapper.notLike(StringUtils.camelToUnderline(name), value));
        }
        if (MapUtils.isNotEmpty(params.getInMap())) {
            params.getInMap().forEach((name, value) -> queryWrapper.in(StringUtils.camelToUnderline(name), value));
        }
        if (MapUtils.isNotEmpty(params.getNotInMap())) {
            params.getNotInMap().forEach((name, value) -> queryWrapper.notIn(StringUtils.camelToUnderline(name), value));
        }
        if (CollectionUtils.isNotEmpty(params.getNulls())) {
            params.getNulls().forEach(name -> queryWrapper.isNull(StringUtils.camelToUnderline(name)));
        }
        if (CollectionUtils.isNotEmpty(params.getNotNulls())) {
            params.getNotNulls().forEach(name -> queryWrapper.isNotNull(StringUtils.camelToUnderline(name)));
        }
        if (CollectionUtils.isNotEmpty(params.getOrders())) {
            params.getOrders().forEach(order -> {
                String[] arr = OrderUtils.parse(order);
                if (arr != null && ArrayUtils.isNotEmpty(arr)) {
                    if (OrderUtils.isAsc(arr[1])) {
                        queryWrapper.orderByAsc(StringUtils.camelToUnderline(arr[0]));
                    } else {
                        queryWrapper.orderByDesc(StringUtils.camelToUnderline(arr[0]));
                    }
                }
            });
        }
    }

    public enum WrapperKeyword implements ISqlSegment {
        LEFT_BRACKET(StringPool.LEFT_BRACKET),
        RIGHT_BRACKET(StringPool.RIGHT_BRACKET);

        private final String keyword;

        WrapperKeyword(final String keyword) {
            this.keyword = keyword;
        }

        @Override
        public String getSqlSegment() {
            return keyword;
        }
    }
}
