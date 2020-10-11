package com.xzixi.framework.webapp.config.security;

import com.xzixi.framework.webapp.util.WebUtils;
import com.xzixi.framework.boot.webmvc.model.Result;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证失败
 *
 * @author 薛凌康
 */
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        String message;
        if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
            message = "用户名或密码错误！";
        } else if (exception instanceof DisabledException) {
            message = "账户被禁用！";
        } else {
            message = "登录失败！";
        }
        Result<?> result = new Result<>(401, message, null);
        WebUtils.printJson(response, result);
    }
}
