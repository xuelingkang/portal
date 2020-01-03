package com.xzixi.self.portal.webapp.config.swagger2;

import com.xzixi.self.portal.webapp.util.ResponseWrapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.xzixi.self.portal.webapp.constant.SecurityConstant.AUTHENTICATION_HEADER_NAME;

/**
 * swagger过滤器
 *
 * @author 薛凌康
 */
public class Swagger2Filter implements Filter {

    /**
     * 拦截api信息，动态添加认证接口
     *
     * @param request ServletRequest
     * @param response ServletResponse
     * @param chain FilterChain
     * @throws IOException IOException
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
        if (content.length>0) {
            String str = new String(content, StandardCharsets.UTF_8);
            JSONObject swagger = JSONObject.fromObject(str);
            JSONArray tags = swagger.getJSONArray("tags");
            tags.add(tokenTag());
            JSONObject paths = swagger.getJSONObject("paths");
            paths.put("/login", loginPath());
            paths.put("/logout", logoutPath());
            // 把返回值输出到客户端
            ServletOutputStream out = response.getOutputStream();
            response.setContentLength(-1);
            out.write(swagger.toString().getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }

    private JSONObject tokenTag() {
        return JSONObject.fromObject(
                        "{"+
                        "    \"description\": \"Authorization\","+
                        "    \"name\": \"认证\""+
                        "}");
    }

    private JSONObject loginPath() {
        return JSONObject.fromObject(
                        "{"+
                        "    \"post\": {"+
                        "        \"tags\": [\"认证\"],"+
                        "        \"summary\": \"登录\","+
                        "        \"operationId\": \"loginUsingPOST\","+
                        "        \"produces\": [\"application/json;charset=UTF-8\"],"+
                        "        \"responses\": {"+
                        "            \"200\": {"+
                        "                \"description\": \"OK\""+
                        "            },"+
                        "            \"401\": {"+
                        "                \"description\": \"Unauthorized\""+
                        "            },"+
                        "            \"403\": {"+
                        "                \"description\": \"Forbidden\""+
                        "            },"+
                        "            \"404\": {"+
                        "                \"description\": \"Not Found\""+
                        "            }"+
                        "        },"+
                        "        \"parameters\": [{"+
                        "            \"name\": \"username\","+
                        "            \"in\": \"query\","+
                        "            \"description\": \"用户名\","+
                        "            \"required\": true,"+
                        "            \"type\": \"string\""+
                        "        },"+
                        "        {"+
                        "            \"name\": \"password\","+
                        "            \"in\": \"query\","+
                        "            \"description\": \"密码\","+
                        "            \"required\": true,"+
                        "            \"type\": \"string\""+
                        "        }]"+
                        "    }"+
                        "}");
    }

    private JSONObject logoutPath() {
        return JSONObject.fromObject(
                        "{"+
                        "    \"get\": {"+
                        "        \"tags\": [\"认证\"],"+
                        "        \"summary\": \"登出\","+
                        "        \"operationId\": \"logoutUsingGET\","+
                        "        \"produces\": [\"application/json;charset=UTF-8\"],"+
                        "        \"parameters\": [{"+
                        "            \"name\": \""+AUTHENTICATION_HEADER_NAME+"\","+
                        "            \"in\": \"header\","+
                        "            \"description\": \"认证参数\","+
                        "            \"required\": true,"+
                        "            \"type\": \"string\""+
                        "        }],"+
                        "        \"responses\": {"+
                        "            \"200\": {"+
                        "                \"description\": \"OK\""+
                        "            },"+
                        "            \"401\": {"+
                        "                \"description\": \"Unauthorized\""+
                        "            },"+
                        "            \"403\": {"+
                        "                \"description\": \"Forbidden\""+
                        "            },"+
                        "            \"404\": {"+
                        "                \"description\": \"Not Found\""+
                        "            }"+
                        "        }"+
                        "    }"+
                        "}");
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}
