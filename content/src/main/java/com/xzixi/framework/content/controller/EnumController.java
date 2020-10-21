package com.xzixi.framework.content.controller;

import com.xzixi.framework.common.model.vo.EnumVO;
import com.xzixi.framework.boot.webmvc.exception.ClientException;
import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.content.service.IEnumService;
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

import static com.xzixi.framework.common.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;

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
