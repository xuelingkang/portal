package com.xzixi.self.portal.webapp.config.security;

import com.xzixi.self.portal.webapp.framework.model.Result;
import com.xzixi.self.portal.webapp.model.po.Token;
import com.xzixi.self.portal.webapp.model.vo.TokenVO;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.service.IUserService;
import com.xzixi.self.portal.webapp.data.ITokenData;
import com.xzixi.self.portal.webapp.framework.util.ResponseUtil;
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
    private ITokenData tokenData;
    @Autowired
    private IUserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserVO user = userDetails.getUser();

        // 保存token
        Token token = tokenData.saveToken(user.getId());

        // 返回token
        TokenVO tokenVO = new TokenVO(token).setUser(user.ignoreProperties("password"));
        Result<TokenVO> result = new Result<>(200, "登录成功！", tokenVO);
        ResponseUtil.printJson(response, result);

        // 设置登录时间
        user.setLoginTime(token.getLoginTime());
        userService.updateById(user);
    }
}
