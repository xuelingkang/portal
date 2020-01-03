package com.xzixi.self.portal.webapp.util;

import com.alibaba.fastjson.JSON;
import com.xzixi.self.portal.framework.model.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author 薛凌康
 */
public class ResponseUtil {

    public static void printJson(HttpServletResponse response, Result<?> result) {
        response.setContentType("application/json;charset=utf-8");
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
