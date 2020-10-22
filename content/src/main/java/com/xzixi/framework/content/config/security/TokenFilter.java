package com.xzixi.framework.content.config.security;

import com.xzixi.framework.common.constant.SecurityConstant;
import com.xzixi.framework.common.model.po.Token;
import com.xzixi.framework.common.model.po.User;
import com.xzixi.framework.common.model.vo.UserVO;
import com.xzixi.framework.content.service.ITokenService;
import com.xzixi.framework.content.service.IUserService;
import com.xzixi.framework.content.util.WebUtils;
import com.xzixi.framework.boot.webmvc.model.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 薛凌康
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    private static final Long MINUTES_10 = 10 * 60 * 1000L;
    @Autowired
    private ITokenService tokenService;
    @Autowired
    private IUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String signature = WebUtils.getHeaderOrParameter(request, SecurityConstant.AUTHENTICATION_HEADER_NAME, SecurityConstant.AUTHENTICATION_PARAMETER_NAME);
        if (StringUtils.isNotEmpty(signature) && !Objects.equals(SecurityConstant.NULL_TOKEN, signature)) {
            try {
                Token token = tokenService.getToken(signature);
                if (token != null) {
                    token = checkExpireTime(token);
                    setAuthentication(token, response);
                }
            } catch (Exception e) {
                Result<?> result = new Result<>(401, "非法认证！", null);
                WebUtils.printJson(response, result);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 将用户信息保存到security上下文
     *
     * @param token token对象
     */
    private void setAuthentication(Token token, HttpServletResponse response) {
        User user = userService.getById(token.getUserId(), false);

        if (user == null) {
            Result<?> result = new Result<>(401, "账户不存在或已被删除！", null);
            WebUtils.printJson(response, result);
            return;
        }

        // 检查是否激活
        if (!user.getActivated()) {
            Result<?> result = new Result<>(401, "账户未激活！", null);
            WebUtils.printJson(response, result);
            return;
        }

        // 检查是否锁定
        if (user.getLocked()) {
            Result<?> result = new Result<>(401, "账户已被锁定！", null);
            WebUtils.printJson(response, result);
            return;
        }

        UserVO userVO = userService.buildVO(user, new UserVO.BuildOption(true, true));

        UserDetailsImpl userDetails = new UserDetailsImpl(userVO);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 过期时间与当前时间对比，临近过期10分钟内的话，刷新token
     *
     * @param token Token
     * @return Token
     */
    private Token checkExpireTime(Token token) {
        long expireTime = token.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MINUTES_10) {
            // 刷新token
            token = tokenService.refreshToken(token.getSignature());
        }
        return token;
    }
}