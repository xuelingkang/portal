package com.xzixi.self.portal.framework.service;

import com.xzixi.self.portal.framework.data.IBaseData;
import com.xzixi.self.portal.framework.model.BaseModel;

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
    default boolean updateBatchByIdIgnoreNullProps(Collection<T> entityList) {
        return updateBatchByIdIgnoreNullProps(entityList, 1000);
    }

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
