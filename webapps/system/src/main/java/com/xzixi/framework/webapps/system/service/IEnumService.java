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

package com.xzixi.framework.webapps.system.service;

import com.xzixi.framework.webapps.common.model.vo.EnumVO;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author 薛凌康
 */
public interface IEnumService {

    String ENUM_SCAN = "classpath:com/xzixi/framework/webapps/common/model/enums/**.class";
    Set<EnumVO> ENUMS = new HashSet<>();

    /**
     * 查询所有枚举项
     *
     * @return 所有枚举项
     */
    default Set<EnumVO> listAll() {
        return ENUMS;
    }

    /**
     * 根据枚举类名查询枚举项
     *
     * @param name 枚举类名
     * @return 枚举项集合
     */
    default EnumVO listByName(String name) {
        return ENUMS.stream().filter(enumVO -> Objects.equals(name, enumVO.getName())).findFirst().orElse(null);
    }
}
