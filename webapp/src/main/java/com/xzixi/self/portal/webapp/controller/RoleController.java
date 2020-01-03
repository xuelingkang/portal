package com.xzixi.self.portal.webapp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzixi.self.portal.framework.exception.ServerException;
import com.xzixi.self.portal.framework.model.Result;
import com.xzixi.self.portal.framework.util.BeanUtils;
import com.xzixi.self.portal.webapp.model.params.RoleSearchParams;
import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.model.po.RoleAuthorityLink;
import com.xzixi.self.portal.webapp.model.valid.RoleSave;
import com.xzixi.self.portal.webapp.model.valid.RoleUpdate;
import com.xzixi.self.portal.webapp.model.vo.RoleVO;
import com.xzixi.self.portal.webapp.service.IRoleAuthorityLinkService;
import com.xzixi.self.portal.webapp.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/role", produces="application/json; charset=UTF-8")
@Api(tags="角色")
@Validated
public class RoleController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleAuthorityLinkService roleAuthorityLinkService;

    @GetMapping
    @ApiOperation(value = "分页查询角色")
    public Result<IPage<Role>> page(RoleSearchParams searchParams) {
        searchParams.setDefaultOrderItems("seq asc");
        IPage<Role> page = roleService.page(searchParams.buildPageParams(), searchParams.buildQueryWrapper());
        return new Result<>(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询角色")
    public Result<RoleVO> getById(
            @ApiParam(value = "角色id", required = true) @NotNull(message = "角色id不能为空！") @PathVariable Integer id) {
        RoleVO roleVO = roleService.buildRoleVO(id);
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
        List<RoleAuthorityLink> oldLinks = roleAuthorityLinkService.list(new QueryWrapper<>(new RoleAuthorityLink().setRoleId(id)));
        boolean result = roleAuthorityLinkService.merge(newLinks, oldLinks, (sources, target) -> sources.stream()
                .filter(source -> source.getAuthorityId() != null && source.getAuthorityId().equals(target.getAuthorityId()))
                .findFirst().orElse(null));
        if (result) {
            return new Result<>();
        }
        throw new ServerException(newLinks, "更新角色权限失败！");
    }
}
