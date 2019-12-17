package com.xzixi.self.portal.webapp.security;

import com.xzixi.self.portal.webapp.model.Result;
import com.xzixi.self.portal.webapp.service.data.ITokenData;
import com.xzixi.self.portal.webapp.util.RequestUtil;
import com.xzixi.self.portal.webapp.util.ResponseUtil;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.xzixi.self.portal.webapp.constant.SecurityConstant.AUTHENTICATION_HEADER_NAME;
import static com.xzixi.self.portal.webapp.constant.SecurityConstant.AUTHENTICATION_PARAMETER_NAME;

/**
 * 退出登录
 *
 * @author 薛凌康
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private ITokenData tokenData;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String signature = RequestUtil.getHeaderOrParameter(request, AUTHENTICATION_HEADER_NAME, AUTHENTICATION_PARAMETER_NAME);
        if (StringUtils.isNotBlank(signature) && !"null".equals(signature)) {
            try {
                tokenData.deleteToken(signature);
            } catch (MalformedJwtException e) {
                Result<?> result = new Result<>(401, "非法认证！", null);
                ResponseUtil.printJson(response, result);
                return;
            }
        }
        Result<?> result = new Result<>(200, "退出成功！", null);
        ResponseUtil.printJson(response, result);
    }
}
