package com.xzixi.framework.webapps.sso.controller;

import com.xzixi.framework.boot.webmvc.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

import static com.xzixi.framework.webapps.common.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;

/**
 * @author xuelingkang
 * @date 2020-10-25
 */
@RestController
@RequestMapping(value = "/password", produces = RESPONSE_MEDIA_TYPE)
@Api(tags = "密码")
@Validated
public class PasswordController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/encode")
    @ApiOperation(value = "加密")
    public Result<String> encode(@ApiParam(value = "明文", required = true) @NotBlank(message = "明文不能为空") String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        return new Result<>(encodedPassword);
    }

    @GetMapping("/matches")
    @ApiOperation(value = "比对密码")
    public Result<Boolean> matches(@ApiParam(value = "明文", required = true) @NotBlank(message = "明文不能为空") String rawPassword,
                                   @ApiParam(value = "密文", required = true) @NotBlank(message = "密文不能为空") String encodedPassword) {
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        return new Result<>(matches);
    }
}
