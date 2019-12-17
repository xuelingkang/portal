package com.xzixi.self.portal.webapp.config.filter;

import com.xzixi.self.portal.webapp.base.util.RequestUtil;
import com.xzixi.self.portal.webapp.base.util.ResponseUtil;
import com.xzixi.self.portal.webapp.config.security.UserDetailsImpl;
import com.xzixi.self.portal.webapp.data.ITokenData;
import com.xzixi.self.portal.webapp.model.Result;
import com.xzixi.self.portal.webapp.model.po.Token;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.service.IUserService;
import io.jsonwebtoken.MalformedJwtException;
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

import static com.xzixi.self.portal.webapp.base.constant.SecurityConstant.AUTHENTICATION_HEADER_NAME;
import static com.xzixi.self.portal.webapp.base.constant.SecurityConstant.AUTHENTICATION_PARAMETER_NAME;

/**
 * @author 薛凌康
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    private static final Long MINUTES_10 = 10 * 60 * 1000L;
    @Autowired
    private ITokenData tokenData;
    @Autowired
    private IUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String signature = RequestUtil.getHeaderOrParameter(request, AUTHENTICATION_HEADER_NAME, AUTHENTICATION_PARAMETER_NAME);
        if (StringUtils.isNotEmpty(signature) && !"null".equals(signature)) {
            try {
                Token token = tokenData.getToken(signature);
                if (token != null) {
                    token = checkExpireTime(token);
                    setAuthentication(token, response);
                }
            } catch (MalformedJwtException e) {
                Result<?> result = new Result<>(401, "非法认证！", null);
                ResponseUtil.printJson(response, result);
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
        User user = userService.getById(token.getUserId());

        // 检查是否冻结
        if (user.getLocked()) {
            Result<?> result = new Result<>(401, "账户被冻结！", null);
            ResponseUtil.printJson(response, result);
        }

        UserVO userVO = userService.buildUserVO(user);

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
            token = tokenData.refreshToken(token.getSignature());
        }
        return token;
    }
}
