package com.xzixi.self.portal.framework.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 构建VO对象
 *
 * @author 薛凌康
 */
public interface IVoService<PO, VO, O> {

    /**
     * 根据id查询
     *
     * @param id PO对象id
     * @return PO对象
     */
    PO getById(Serializable id);

    /**
     * 构建VO
     *
     * @param id PO对象id
     * @param option 选项
     * @return VO
     */
    default VO buildVO(Integer id, O option) {
        return buildVO(getById(id), option);
    }

    /**
     * 构建VO
     *
     * @param entity PO对象
     * @param option 选项
     * @return VO
     */
    VO buildVO(PO entity, O option);

    /**
     * 构建VO集合
     *
     * @param entities PO对象集合
     * @param option 选项
     * @return List&lt;VO>
     */
    List<VO> buildVO(Collection<PO> entities, O option);

    /**
     * 构建VO分页
     *
     * @param poPage PO对象分页
     * @param option 选项
     * @return IPage&lt;VO>
     */
    default IPage<VO> buildVO(IPage<PO> poPage, O option) {
        if (poPage == null) {
            return null;
        }
        List<VO> voList = buildVO(poPage.getRecords(), option);
        IPage<VO> voPage = new Page<>(poPage.getCurrent(), poPage.getSize(), poPage.getTotal(), poPage.isSearchCount());
        voPage.setRecords(voList);
        return voPage;
    }
}
