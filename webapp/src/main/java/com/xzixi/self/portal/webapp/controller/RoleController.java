package com.xzixi.self.portal.webapp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzixi.self.portal.webapp.framework.exception.ServerException;
import com.xzixi.self.portal.webapp.framework.model.Result;
import com.xzixi.self.portal.webapp.framework.util.BeanUtils;
import com.xzixi.self.portal.webapp.model.params.RoleSearchParams;
import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.model.valid.RoleSave;
import com.xzixi.self.portal.webapp.model.valid.RoleUpdate;
import com.xzixi.self.portal.webapp.model.vo.RoleVO;
import com.xzixi.self.portal.webapp.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/role", produces="application/json; charset=UTF-8")
@Api(tags="角色")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping
    @ApiOperation(value = "分页查询角色")
    public Result<IPage<Role>> page(RoleSearchParams searchParams) {
        searchParams.setDefaultOrderItems(new String[]{"seq true"});
        IPage<Role> page = roleService.page(searchParams.buildPageParams(), searchParams.buildQueryWrapper());
        return new Result<>(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询角色")
    public Result<Role> getById(@PathVariable @NotNull(message = "角色id不能为空！") Integer id) {
        Role role = roleService.getById(id);
        return new Result<>(role);
    }

    @PostMapping
    @ApiOperation(value = "保存角色")
    public Result<RoleVO> save(@Validated({RoleSave.class}) Role role) {
        // 保存角色
        if (!roleService.save(role)) {
            throw new ServerException();
        }
        // 构建RoleVO
        RoleVO roleVO = roleService.buildRoleVO(role);
        return new Result<>(roleVO);
    }

    @PutMapping
    @ApiOperation(value = "更新角色")
    public Result<?> update(@Validated({RoleUpdate.class}) Role role) {
        Role roleData = roleService.getById(role.getId());
        BeanUtils.copyPropertiesIgnoreNull(role, roleData);
        if (roleService.updateById(roleData)) {
            return new Result<>();
        }
        throw new ServerException();
    }
}
