package com.xzixi.self.portal.webapp.config.swagger2;

import com.xzixi.self.portal.framework.exception.ProjectException;
import com.xzixi.self.portal.webapp.util.ResponseWrapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.xzixi.self.portal.webapp.constant.Swagger2Constant.AUTHENTICATION_TEMPLATE;

/**
 * swagger过滤器
 *
 * @author 薛凌康
 */
public class Swagger2Filter implements Filter {

    private static JSONObject tag;
    private static JSONObject login;
    private static JSONObject logout;

    /**
     * 拦截api信息，动态添加认证接口
     *
     * @param request  ServletRequest
     * @param response ServletResponse
     * @param chain    FilterChain
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 转换成代理类
        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
        // 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理
        chain.doFilter(request, responseWrapper);
        // 获取返回值
        byte[] content = responseWrapper.getContent();
        // 判断是否有值
        if (content.length > 0) {
            String str = new String(content, StandardCharsets.UTF_8);
            JSONObject swagger = JSONObject.fromObject(str);
            JSONArray tags = swagger.getJSONArray("tags");
            tags.add(tag);
            JSONObject paths = swagger.getJSONObject("paths");
            paths.put("/login", login);
            paths.put("/logout", logout);
            // 把返回值输出到客户端
            ServletOutputStream out = response.getOutputStream();
            response.setContentLength(-1);
            out.write(swagger.toString().getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }

    static {
        ClassPathResource resource = new ClassPathResource(AUTHENTICATION_TEMPLATE);
        String template;
        try {
            InputStream in = resource.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            template = out.toString(StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            throw new ProjectException("读取模板失败！", e);
        }
        JSONObject authentication = JSONObject.fromObject(template);
        tag = authentication.getJSONObject("tag");
        login = authentication.getJSONObject("login");
        logout = authentication.getJSONObject("logout");
    }
}
