/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.sso.server.util;

import com.alibaba.fastjson.JSON;
import com.xzixi.framework.boot.core.exception.ProjectException;
import com.xzixi.framework.boot.core.model.Result;
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
