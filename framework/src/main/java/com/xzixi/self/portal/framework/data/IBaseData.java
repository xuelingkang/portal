package com.xzixi.self.portal.framework.data;

import com.xzixi.self.portal.framework.exception.ServerException;
import com.xzixi.self.portal.framework.model.BaseModel;
import com.xzixi.self.portal.framework.model.search.Pagination;
import com.xzixi.self.portal.framework.model.search.QueryParams;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
public interface IBaseData<T extends BaseModel> {

    /**
     * 根据id查询
     *
     * @param id 主键
     * @return 实体对象
     */
    T getById(Serializable id);

    /**
     * 根据id集合查询
     *
     * @param ids 主键集合
     * @return 实体集合
     */
    Collection<T> listByIds(Collection<? extends Serializable> ids);

    /**
     * 根据条件查询一个
     *
     * @param params 查询条件
     * @param throwEx 当查询结果是多个时是否抛出异常
     * @return 实体对象
     */
    default T getOne(QueryParams<T> params, boolean throwEx) {
        List<T> models = list(params);
        if (CollectionUtils.isEmpty(models)) {
            return null;
        }
        if (throwEx && models.size() > 1) {
            throw new ServerException(params, String.format("查询结果应该为1个，却查询到%s个", models.size()));
        }
        return models.get(0);
    }

    /**
     * 根据条件查询一个，当查询结果是多个时抛出异常
     *
     * @param params 查询条件
     * @return 实体对象
     */
    default T getOne(QueryParams<T> params) {
        return getOne(params, true);
    }

    /**
     * 查询所有记录
     *
     * @return 所有记录
     */
    default List<T> listAll() {
        return list(new QueryParams<>());
    }

    /**
     * 根据条件查询
     *
     * @param params 查询条件
     * @return 实体集合
     */
    List<T> list(QueryParams<T> params);

    /**
     * 分页查询
     *
     * @param pagination 分页模型
     * @param params 查询参数
     * @return 分页查询结果
     */
    Pagination<T> page(Pagination<T> pagination, QueryParams<T> params);

    /**
     * 查询所有记录个数
     *
     * @return 所有记录个数
     */
    default int countAll() {
        return count(new QueryParams<>());
    }

    /**
     * 根据条件查询个数
     *
     * @param params 查询条件
     * @return 个数
     */
    int count(QueryParams<T> params);

    /**
     * 保存
     *
     * @param model 实体对象
     * @return {@code true} 成功 {@code false} 失败
     */
    boolean save(T model);

    /**
     * 批量保存
     *
     * @param models 实体集合
     * @return {@code true} 成功 {@code false} 失败
     */
    default boolean defaultSaveBatch(Collection<T> models) {
        return saveOrUpdateBatch(models, defaultBatchSize());
    }

    /**
     * 批量保存
     *
     * @param models 实体集合
     * @param batchSize 批次大小
     * @return {@code true} 成功 {@code false} 失败
     */
    boolean saveBatch(Collection<T> models, int batchSize);

    /**
     * 保存或更新
     *
     * @param model 实体对象
     * @return {@code true} 成功 {@code false} 失败
     */
    boolean saveOrUpdate(T model);

    /**
     * 批量保存或更新
     *
     * @param models 实体集合
     * @return {@code true} 成功 {@code false} 失败
     */
    default boolean defaultSaveOrUpdateBatch(Collection<T> models) {
        return saveOrUpdateBatch(models, defaultBatchSize());
    }

    /**
     * 批量保存或更新
     *
     * @param models 实体集合
     * @param batchSize 批次大小
     * @return {@code true} 成功 {@code false} 失败
     */
    boolean saveOrUpdateBatch(Collection<T> models, int batchSize);

    /**
     * 根据id更新
     *
     * @param model 实体对象
     * @return {@code true} 成功 {@code false} 失败
     */
    boolean updateById(T model);

    /**
     * 根据id批量更新
     *
     * @param models 实体集合
     * @return {@code true} 成功 {@code false} 失败
     */
    default boolean defaultUpdateBatchById(Collection<T> models) {
        return updateBatchById(models, defaultBatchSize());
    }

    /**
     * 根据id批量更新
     *
     * @param models 实体集合
     * @param batchSize 批次大小
     * @return {@code true} 成功 {@code false} 失败
     */
    boolean updateBatchById(Collection<T> models, int batchSize);

    /**
     * 根据id删除
     *
     * @param id 主键
     * @return {@code true} 成功 {@code false} 失败
     */
    boolean removeById(Serializable id);

    /**
     * 根据id集合删除
     *
     * @param ids 主键集合
     * @return {@code true} 成功 {@code false} 失败
     */
    boolean removeByIds(Collection<? extends Serializable> ids);

    /**
     * 默认批次大小
     *
     * @return int
     */
    int defaultBatchSize();
}
