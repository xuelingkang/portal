package com.xzixi.self.portal.webapp.config.security;

import com.xzixi.self.portal.webapp.framework.util.ResponseUtil;
import com.xzixi.self.portal.webapp.framework.model.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限不足
 *
 * @author 薛凌康
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) {
        Result<?> result = new Result<>(403, exception.getMessage(), null);
        ResponseUtil.printJson(response, result);
    }
}
