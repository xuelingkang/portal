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

package com.xzixi.framework.webapps.system.controller;

import com.xzixi.framework.boot.core.exception.ClientException;
import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.webapps.common.model.vo.EnumVO;
import com.xzixi.framework.webapps.system.service.IEnumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

import static com.xzixi.framework.webapps.common.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/enum", produces = RESPONSE_MEDIA_TYPE)
@Api(tags = "枚举")
@Validated
public class EnumController {

    @Autowired
    private IEnumService enumService;

    @GetMapping
    @ApiOperation(value = "获取所有枚举")
    public Result<Collection<EnumVO>> listAll() {
        Collection<EnumVO> enums = enumService.listAll();
        return new Result<>(enums);
    }

    @GetMapping("/{name}")
    @ApiOperation(value = "根据类名获取枚举")
    public Result<EnumVO> listByName(
            @ApiParam(value = "枚举类名", required = true)
            @NotBlank(message = "枚举类名不能为空")
            @PathVariable String name) {
        EnumVO enumVO = enumService.listByName(name);
        if (enumVO == null) {
            throw new ClientException(400, "枚举不存在！");
        }
        return new Result<>(enumVO);
    }
}
