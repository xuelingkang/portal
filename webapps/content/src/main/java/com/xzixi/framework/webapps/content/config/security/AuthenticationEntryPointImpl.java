package com.xzixi.framework.webapps.content.config.security;

import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.webapps.content.util.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证异常
 *
 * @author 薛凌康
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        Result<?> result = new Result<>(401, exception.getMessage(), null);
        WebUtils.printJson(response, result);
    }
}
