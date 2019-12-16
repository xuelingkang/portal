package com.xzixi.self.portal.webapp.filter;

import com.xzixi.self.portal.webapp.model.Result;
import com.xzixi.self.portal.webapp.model.po.Token;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.security.UserDetailsImpl;
import com.xzixi.self.portal.webapp.service.business.IUserBusiness;
import com.xzixi.self.portal.webapp.service.data.ITokenData;
import com.xzixi.self.portal.webapp.util.RequestUtil;
import com.xzixi.self.portal.webapp.util.ResponseUtil;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static com.xzixi.self.portal.webapp.constant.SecurityConstant.AUTHENTICATION_HEADER_NAME;
import static com.xzixi.self.portal.webapp.constant.SecurityConstant.AUTHENTICATION_PARAMETER_NAME;

/**
 * @author 薛凌康
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    private static final Long MINUTES_10 = 10 * 60 * 1000L;
    private static final RequestMatcher[] IGNORE_PATH = {
            // 登录
            new AntPathRequestMatcher("/login", "POST"),
            // 登出
            new AntPathRequestMatcher("/logout", "GET"),
            // 注册
            new AntPathRequestMatcher("/website/user", "POST")
    };
    @Autowired
    private ITokenData tokenData;
    @Autowired
    private IUserBusiness userBusiness;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenStr = RequestUtil.getHeaderOrParameter(request, AUTHENTICATION_HEADER_NAME, AUTHENTICATION_PARAMETER_NAME);
        if (StringUtils.isNotEmpty(tokenStr) && !"null".equals(tokenStr)) {
            try {
                Token token = tokenData.getToken(tokenStr);
                if (token == null) {
                    boolean ignore = Arrays.stream(IGNORE_PATH).anyMatch(requestMatcher -> requestMatcher.matches(request));
                    if (!ignore) {
                        Result<?> result = new Result<>(401, "认证信息无效！", null);
                        ResponseUtil.printJson(response, result);
                    }
                } else {
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
        User user = userBusiness.getById(token.getUserId());

        // 检查是否冻结
        if (user.getLocked()) {
            Result<?> result = new Result<>(401, "账户被冻结！", null);
            ResponseUtil.printJson(response, result);
        }

        UserVO userVO = userBusiness.buildUserVO(user);

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
            token = tokenData.refreshToken(token.getTokenStr());
        }
        return token;
    }
}
