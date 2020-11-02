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

package com.xzixi.framework.boot.webmvc.service;

import com.xzixi.framework.boot.webmvc.model.BaseModel;
import com.xzixi.framework.boot.webmvc.model.search.Pagination;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 构建VO对象
 *
 * @author 薛凌康
 */
public interface IVoService<PO extends BaseModel, VO extends BaseModel, O> {

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
    default Pagination<VO> buildVO(Pagination<PO> poPage, O option) {
        if (poPage == null) {
            return null;
        }
        List<VO> voList = buildVO(poPage.getRecords(), option);
        Pagination<VO> voPage = new Pagination<>(poPage.getCurrent(), poPage.getSize(), poPage.getTotal(), poPage.isSearchCount());
        voPage.setRecords(voList);
        return voPage;
    }
}
