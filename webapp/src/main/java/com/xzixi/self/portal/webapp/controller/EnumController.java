package com.xzixi.self.portal.webapp.controller;

import com.xzixi.self.portal.webapp.model.Result;
import com.xzixi.self.portal.webapp.model.vo.EnumVO;
import com.xzixi.self.portal.webapp.service.IEnumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/enum", produces="application/json; charset=UTF-8")
@Api(tags="枚举")
public class EnumController {

    @Autowired
    private IEnumService enumService;

    @GetMapping
    @ApiOperation(value = "获取所有枚举")
    public Result<Set<EnumVO>> listAll() {
        return new Result<Set<EnumVO>>().setData(enumService.listAll());
    }

    @GetMapping("/{name}")
    @ApiOperation(value = "根据类名获取枚举")
    public Result<EnumVO> listByName(
            @ApiParam(value = "枚举类名", required = true)
            @NotBlank(message = "枚举类名不能为空")
            @PathVariable String name) {
        return new Result<EnumVO>().setData(enumService.listByName(name));
    }
}
