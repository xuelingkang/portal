package com.xzixi.framework.backend.controller;

import com.xzixi.framework.backend.model.params.AuthoritySearchParams;
import com.xzixi.framework.backend.model.po.Authority;
import com.xzixi.framework.backend.model.valid.AuthoritySave;
import com.xzixi.framework.backend.model.valid.AuthorityUpdate;
import com.xzixi.framework.backend.model.vo.AuthorityVO;
import com.xzixi.framework.boot.webmvc.exception.ServerException;
import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.boot.webmvc.model.search.Pagination;
import com.xzixi.framework.boot.webmvc.util.BeanUtils;
import com.xzixi.framework.backend.service.IAuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.xzixi.framework.backend.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/authority", produces = RESPONSE_MEDIA_TYPE)
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
