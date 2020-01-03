package com.xzixi.self.portal.webapp.config.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.framework.exception.ClientException;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.service.IUserService;
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
        User user = userService.getOne(new QueryWrapper<>(new User().setUsername(username)));
        if (user==null) {
            throw new UsernameNotFoundException("用户名："+ username + "不存在！");
        }

        UserVO userVO = userService.buildVO(user, new UserVO.BuildOption(true, true));
        return new UserDetailsImpl(userVO);
    }
}
