package com.xzixi.self.portal.webapp.config.security;

import com.xzixi.self.portal.framework.model.Result;
import com.xzixi.self.portal.webapp.model.po.Token;
import com.xzixi.self.portal.webapp.model.vo.TokenVO;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.service.ITokenService;
import com.xzixi.self.portal.webapp.service.IUserService;
import com.xzixi.self.portal.webapp.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证成功
 *
 * @author 薛凌康
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Autowired
    private ITokenService tokenService;
    @Autowired
    private IUserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserVO user = userDetails.getUser();

        // 保存token
        Token token = tokenService.saveToken(user.getId());

        // 返回token
        user.setPassword(null);
        TokenVO tokenVO = new TokenVO(token).setUser(user);
        Result<TokenVO> result = new Result<>(200, "登录成功！", tokenVO);
        WebUtil.printJson(response, result);

        // 设置登录时间
        user.setLoginTime(token.getLoginTime());
        userService.updateByIdIgnoreNullProps(user);
    }
}
