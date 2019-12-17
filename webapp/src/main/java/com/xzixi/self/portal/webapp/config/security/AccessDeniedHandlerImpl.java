package com.xzixi.self.portal.webapp.config.security;

import com.xzixi.self.portal.webapp.model.Result;
import com.xzixi.self.portal.webapp.base.util.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足
 *
 * @author 薛凌康
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
        Result<?> result = new Result<>(403, exception.getMessage(), null);
        ResponseUtil.printJson(response, result);
    }
}
