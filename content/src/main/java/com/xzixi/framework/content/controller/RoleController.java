package com.xzixi.framework.content.controller;

import com.xzixi.framework.common.model.params.RoleSearchParams;
import com.xzixi.framework.common.model.po.Role;
import com.xzixi.framework.common.model.po.RoleAuthorityLink;
import com.xzixi.framework.common.model.valid.RoleSave;
import com.xzixi.framework.common.model.valid.RoleUpdate;
import com.xzixi.framework.common.model.vo.RoleVO;
import com.xzixi.framework.boot.webmvc.exception.ServerException;
import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.boot.webmvc.model.search.Pagination;
import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import com.xzixi.framework.boot.webmvc.util.BeanUtils;
import com.xzixi.framework.content.service.IRoleAuthorityLinkService;
import com.xzixi.framework.content.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.xzixi.framework.common.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/role", produces = RESPONSE_MEDIA_TYPE)
@Api(tags = "角色")
@Validated
public class RoleController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleAuthorityLinkService roleAuthorityLinkService;

    @GetMapping
    @ApiOperation(value = "分页查询角色")
    public Result<Pagination<RoleVO>> page(RoleSearchParams searchParams) {
        searchParams.setDefaultOrders("seq asc");
        Pagination<Role> rolePage = roleService.page(searchParams.buildPagination(), searchParams.buildQueryParams());
        Pagination<RoleVO> page = roleService.buildVO(rolePage, new RoleVO.BuildOption(false));
        return new Result<>(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询角色")
    public Result<RoleVO> getById(
            @ApiParam(value = "角色id", required = true) @NotNull(message = "角色id不能为空！") @PathVariable Integer id) {
        RoleVO roleVO = roleService.buildVO(id, new RoleVO.BuildOption(true));
        return new Result<>(roleVO);
    }

    @PostMapping
    @ApiOperation(value = "保存角色")
    public Result<?> save(@Validated({RoleSave.class}) Role role) {
        // 保存角色
        if (roleService.save(role)) {
            return new Result<>();
        }
        throw new ServerException(role, "保存角色失败！");
    }

    @PutMapping
    @ApiOperation(value = "更新角色")
    public Result<?> update(@Validated({RoleUpdate.class}) Role role) {
        Role roleData = roleService.getById(role.getId());
        BeanUtils.copyPropertiesIgnoreNull(role, roleData);
        if (roleService.updateById(roleData)) {
            return new Result<>();
        }
        throw new ServerException(role, "更新角色失败！");
    }

    @DeleteMapping
    @ApiOperation(value = "删除角色")
    public Result<?> remove(
            @ApiParam(value = "角色id", required = true) @NotEmpty(message = "角色id不能为空！") @RequestParam List<Integer> ids) {
        roleService.removeRolesByIds(ids);
        return new Result<>();
    }

    @PostMapping("/{id}/authority")
    @ApiOperation(value = "更新角色权限")
    public Result<?> updateRoleAuthority(
            @ApiParam(value = "角色id", required = true) @NotNull(message = "角色id不能为空") @PathVariable Integer id,
            @ApiParam(value = "权限id", required = true) @NotEmpty(message = "权限id不能为空！") @RequestParam List<Integer> authorityIds) {
        List<RoleAuthorityLink> newLinks = authorityIds.stream()
                .map(authorityId -> new RoleAuthorityLink(id, authorityId))
                .collect(Collectors.toList());
        List<RoleAuthorityLink> oldLinks = roleAuthorityLinkService.list(new QueryParams<>(new RoleAuthorityLink().setRoleId(id)));
        boolean result = roleAuthorityLinkService.merge(newLinks, oldLinks, (sources, target) -> sources.stream()
                .filter(source -> Objects.equals(source.getAuthorityId(), target.getAuthorityId()))
                .findFirst().orElse(null));
        if (result) {
            return new Result<>();
        }
        throw new ServerException(newLinks, "更新角色权限失败！");
    }
}
