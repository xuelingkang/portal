package com.xzixi.framework.webapps.sso.server.controller;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.xzixi.framework.webapps.common.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;

/**
 * @author xuelingkang
 * @date 2020-10-25
 */
@RestController
@RequestMapping(value = "/token", produces = RESPONSE_MEDIA_TYPE)
@Api(tags = "token")
@Validated
public class TokenController {

    /*
     * TODO
     *  sso-client验证token
     *  使用accessToken获取用户信息
     *  使用refreshToken刷新accessToken
     */
}
