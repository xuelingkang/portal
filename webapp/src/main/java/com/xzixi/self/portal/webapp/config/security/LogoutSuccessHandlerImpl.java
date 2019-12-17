package com.xzixi.self.portal.webapp.config.security;

import com.xzixi.self.portal.webapp.base.util.RequestUtil;
import com.xzixi.self.portal.webapp.base.util.ResponseUtil;
import com.xzixi.self.portal.webapp.data.ITokenData;
import com.xzixi.self.portal.webapp.model.Result;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.xzixi.self.portal.webapp.base.constant.SecurityConstant.AUTHENTICATION_HEADER_NAME;
import static com.xzixi.self.portal.webapp.base.constant.SecurityConstant.AUTHENTICATION_PARAMETER_NAME;

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
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
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
