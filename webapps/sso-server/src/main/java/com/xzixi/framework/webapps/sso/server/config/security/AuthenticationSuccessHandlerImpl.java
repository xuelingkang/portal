package com.xzixi.framework.webapps.sso.server.config.security;

import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.webapps.common.feign.RemoteUserService;
import com.xzixi.framework.webapps.common.model.po.Token;
import com.xzixi.framework.webapps.common.model.vo.TokenVO;
import com.xzixi.framework.webapps.common.model.vo.UserDetailsImpl;
import com.xzixi.framework.webapps.common.model.vo.UserVO;
import com.xzixi.framework.webapps.sso.server.service.ITokenService;
import com.xzixi.framework.webapps.sso.server.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证成功
 * TODO 认证成功回调应用
 *
 * @author 薛凌康
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    private ITokenService tokenService;
    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserVO user = userDetails.getUser();

        // 保存token
        Token token = tokenService.saveToken(user.getId());

        // 返回token
        user.setPassword(null);
        user.setLoginTime(token.getLoginTime());
        TokenVO tokenVO = new TokenVO(token).setUser(user);
        Result<TokenVO> result = new Result<>(200, "登录成功！", tokenVO);
        WebUtils.printJson(response, result);

        // 设置登录时间
        remoteUserService.updateLoginTime(user.getId(), user.getLoginTime());
    }
}
