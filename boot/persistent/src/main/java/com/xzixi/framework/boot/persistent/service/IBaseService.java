/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.boot.persistent.service;

import com.xzixi.framework.boot.persistent.data.IBaseData;
import com.xzixi.framework.boot.core.model.BaseModel;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IBaseService<T extends BaseModel> extends IBaseData<T> {

    /**
     * 根据id查询
     *
     * @param id id
     * @param throwEx 查询结果为空时是否抛出异常
     * @return T
     */
    T getById(Serializable id, boolean throwEx);

    /**
     * 更新非null属性
     *
     * @param entity 实体对象
     * @return {@code true} 更新成功 {@code false} 更新失败
     */
    boolean updateByIdIgnoreNullProps(T entity);

    /**
     * 根据ID 批量更新非null属性
     *
     * @param entityList 实体对象集合
     * @return {@code true} 更新成功 {@code false} 更新失败
     */
    boolean updateBatchByIdIgnoreNullProps(Collection<T> entityList);

    /**
     * 根据ID 批量更新非null属性
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     * @return {@code true} 更新成功 {@code false} 更新失败
     */
    boolean updateBatchByIdIgnoreNullProps(Collection<T> entityList, int batchSize);

    /**
     * 对新旧数据集进行合并
     *
     * @param newModels 新数据集
     * @param oldModels 旧数据集
     * @param selector 比较器
     * @return {@code true} 合并成功 {@code false} 合并失败
     */
    boolean merge(Collection<T> newModels, Collection<T> oldModels, MergeSelector<T> selector);

    interface MergeSelector<T> {

        /**
         * 查询源数据集中与目标实例匹配的元素
         *
         * @param sources 源数据集
         * @param target 目标实例
         * @return 查询结果，{@code null}表示没有查询到
         */
        T select(Collection<T> sources, T target);
    }
}
