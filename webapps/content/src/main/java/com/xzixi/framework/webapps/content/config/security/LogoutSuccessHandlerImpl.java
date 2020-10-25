package com.xzixi.framework.webapps.content.config.security;

import com.xzixi.framework.webapps.common.constant.SecurityConstant;
import com.xzixi.framework.webapps.content.util.WebUtils;
import com.xzixi.framework.boot.webmvc.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

/**
 * 退出登录
 *
 * @author 薛凌康
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private ITokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String signature = WebUtils.getHeaderOrParameter(request, SecurityConstant.AUTHENTICATION_HEADER_NAME, SecurityConstant.AUTHENTICATION_PARAMETER_NAME);
        if (StringUtils.isNotBlank(signature) && !Objects.equals(SecurityConstant.NULL_TOKEN, signature)) {
            try {
                tokenService.deleteToken(signature);
            } catch (Exception e) {
                Result<?> result = new Result<>(401, "非法认证！", null);
                WebUtils.printJson(response, result);
                return;
            }
        }
        Result<?> result = new Result<>(200, "退出成功！", null);
        WebUtils.printJson(response, result);
    }
}
