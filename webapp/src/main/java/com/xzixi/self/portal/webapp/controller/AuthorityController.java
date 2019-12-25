package com.xzixi.self.portal.webapp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzixi.self.portal.webapp.framework.exception.ServerException;
import com.xzixi.self.portal.webapp.framework.model.Result;
import com.xzixi.self.portal.webapp.framework.util.BeanUtils;
import com.xzixi.self.portal.webapp.model.params.AuthoritySearchParams;
import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.model.valid.AuthoritySave;
import com.xzixi.self.portal.webapp.model.valid.AuthorityUpdate;
import com.xzixi.self.portal.webapp.model.vo.AuthorityVO;
import com.xzixi.self.portal.webapp.service.IAuthorityService;
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
@RequestMapping(value = "/authority", produces="application/json; charset=UTF-8")
@Api(tags="权限")
public class AuthorityController {

    @Autowired
    private IAuthorityService authorityService;

    @GetMapping
    @ApiOperation(value = "分页查询权限")
    public Result<IPage<Authority>> page(AuthoritySearchParams searchParams) {
        searchParams.setDefaultOrderItems(new String[]{"category asc", "seq asc"});
        IPage<Authority> page = authorityService.page(searchParams.buildPageParams(), searchParams.buildQueryWrapper());
        return new Result<>(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询权限")
    public Result<Authority> getById(@PathVariable @NotNull(message = "权限id不能为空！") Integer id) {
        Authority authority = authorityService.getById(id);
        return new Result<>(authority);
    }

    @PostMapping
    @ApiOperation(value = "保存权限")
    public Result<AuthorityVO> save(@Validated({AuthoritySave.class}) Authority authority) {
        // 保存权限
        if (!authorityService.save(authority)) {
            throw new ServerException();
        }
        // 构建AuthorityVO
        AuthorityVO authorityVO = authorityService.buildAuthorityVO(authority);
        return new Result<>(authorityVO);
    }

    @PutMapping
    @ApiOperation(value = "更新权限")
    public Result<?> update(@Validated({AuthorityUpdate.class}) Authority authority) {
        Authority authorityData = authorityService.getById(authority.getId());
        BeanUtils.copyPropertiesIgnoreNull(authority, authorityData);
        if (authorityService.updateById(authorityData)) {
            return new Result<>();
        }
        throw new ServerException();
    }
}
