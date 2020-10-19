package com.xzixi.framework.backend.config.security;

import com.xzixi.framework.boot.webmvc.exception.ClientException;
import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import com.xzixi.framework.backend.model.po.User;
import com.xzixi.framework.backend.model.vo.UserVO;
import com.xzixi.framework.backend.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new ClientException(400, "用户名username不能为空！");
        }
        User user = userService.getOne(new QueryParams<>(new User().setUsername(username)));
        if (user==null) {
            throw new UsernameNotFoundException("用户名："+ username + "不存在！");
        }

        UserVO userVO = userService.buildVO(user, new UserVO.BuildOption(true, true));
        return new UserDetailsImpl(userVO);
    }
}
