package com.xzixi.self.portal.webapp.util;

import com.xzixi.self.portal.framework.exception.ProjectException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 薛凌康
 */
public class RequestUtil {

    public static String getHeaderOrParameter(HttpServletRequest request, String headerName, String parameterName) {
        String value = getHeader(request, headerName);
        if (StringUtils.isEmpty(value)) {
            value = getParameter(request, parameterName);
        }
        return value;
    }

    public static String getParameter(HttpServletRequest request, String name) {
        if (request == null || StringUtils.isBlank(name)) {
            throw new ProjectException("request或参数名不能为空！");
        }
        return request.getParameter(name);
    }

    public static String getHeader(HttpServletRequest request, String name) {
        if (request == null || StringUtils.isBlank(name)) {
            throw new ProjectException("request或参数名不能为空！");
        }
        return request.getHeader(name);
    }
}
