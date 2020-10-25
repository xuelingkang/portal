package com.xzixi.framework.webapps.sso.server.util;

import com.alibaba.fastjson.JSON;
import com.xzixi.framework.boot.webmvc.exception.ProjectException;
import com.xzixi.framework.boot.webmvc.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author 薛凌康
 */
public class WebUtils {

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

    public static void printJson(HttpServletResponse response, Result<?> result) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(result.getCode());
        OutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (out!=null) {
            PrintWriter writer = new PrintWriter(out, true);
            writer.println(JSON.toJSONString(result));
        }
    }
}
