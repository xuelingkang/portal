package com.xzixi.self.portal.webapp.framework.service;

import com.xzixi.self.portal.webapp.framework.data.IBaseData;
import com.xzixi.self.portal.webapp.framework.model.BaseModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IBaseService<T extends BaseModel> extends IBaseData<T> {

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
    @Transactional(rollbackFor = Exception.class)
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
     * @param comparator 比较器
     */
    void merge(Collection<T> newModels, Collection<T> oldModels, MergeComparator<T> comparator);

    interface MergeComparator<T> {

        /**
         * 查询models中与item相等的元素
         *
         * @param models {@link T}集合
         * @param item {@link T}
         * @return models中与item相等的元素
         */
        T find(Collection<T> models, T item);
    }
}