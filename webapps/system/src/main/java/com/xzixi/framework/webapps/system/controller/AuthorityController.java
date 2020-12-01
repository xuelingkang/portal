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

import com.xzixi.framework.boot.core.exception.ServerException;
import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.boot.core.model.search.Pagination;
import com.xzixi.framework.boot.core.util.BeanUtils;
import com.xzixi.framework.webapps.common.constant.ProjectConstant;
import com.xzixi.framework.webapps.common.model.params.AuthoritySearchParams;
import com.xzixi.framework.webapps.common.model.po.Authority;
import com.xzixi.framework.webapps.common.model.valid.AuthoritySave;
import com.xzixi.framework.webapps.common.model.valid.AuthorityUpdate;
import com.xzixi.framework.webapps.common.model.vo.AuthorityVO;
import com.xzixi.framework.webapps.system.service.IAuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/authority", produces = ProjectConstant.RESPONSE_MEDIA_TYPE)
@Api(tags = "权限")
@Validated
public class AuthorityController {

    @Autowired
    private IAuthorityService authorityService;

    @GetMapping
    @ApiOperation(value = "分页查询权限")
    public Result<Pagination<AuthorityVO>> page(AuthoritySearchParams searchParams) {
        searchParams.setDefaultOrders("category asc", "seq asc");
        Pagination<Authority> authorityPage = authorityService.page(searchParams.buildPagination(), searchParams.buildQueryParams());
        Pagination<AuthorityVO> page = authorityService.buildVO(authorityPage, new AuthorityVO.BuildOption());
        return new Result<>(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询权限")
    public Result<AuthorityVO> getById(
            @ApiParam(value = "权限id", required = true) @NotNull(message = "权限id不能为空！") @PathVariable Integer id) {
        AuthorityVO authorityVO = authorityService.buildVO(id, new AuthorityVO.BuildOption());
        return new Result<>(authorityVO);
    }

    @PostMapping
    @ApiOperation(value = "保存权限")
    public Result<?> save(@Validated({AuthoritySave.class}) Authority authority) {
        // 保存权限
        if (authorityService.save(authority)) {
            return new Result<>();
        }
        throw new ServerException(authority, "保存权限失败！");
    }

    @PutMapping
    @ApiOperation(value = "更新权限")
    public Result<?> update(@Validated({AuthorityUpdate.class}) Authority authority) {
        Authority authorityData = authorityService.getById(authority.getId());
        BeanUtils.copyPropertiesIgnoreNull(authority, authorityData);
        if (authorityService.updateById(authorityData)) {
            return new Result<>();
        }
        throw new ServerException(authority, "更新权限失败！");
    }

    @DeleteMapping
    @ApiOperation(value = "删除权限")
    public Result<?> remove(
            @ApiParam(value = "权限id", required = true) @NotEmpty(message = "权限id不能为空！") @RequestParam List<Integer> ids) {
        authorityService.removeAuthoritiesByIds(ids);
        return new Result<>();
    }
}
