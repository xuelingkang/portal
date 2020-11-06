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

package com.xzixi.framework.webapps.common.model.po;

import com.xzixi.framework.boot.core.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关注
 *
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
public class UserLink extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /** 偶像id */
    private Integer idolId;

    /** 粉丝id */
    private Integer followerId;

    /** 关注时间 */
    private Long followTime;

    public UserLink(Integer idolId, Integer followerId) {
        this.idolId = idolId;
        this.followerId = followerId;
    }
}
