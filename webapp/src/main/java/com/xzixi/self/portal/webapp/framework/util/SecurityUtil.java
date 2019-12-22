package com.xzixi.self.portal.webapp.framework.util;

import com.xzixi.self.portal.webapp.config.security.UserDetailsImpl;
import com.xzixi.self.portal.webapp.framework.exception.LogicException;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author 薛凌康
 */
public class SecurityUtil {

    public static UserVO getCurrentUser() {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails == null) {
            return null;
        }
        return userDetails.getUser();
    }

    public static void checkCurrentUser(User user) {
        User currentUser = SecurityUtil.getCurrentUser();
        if (currentUser == null) {
            throw new LogicException(401, "未登录！");
        }
        if (!user.getId().equals(currentUser.getId())) {
            throw new LogicException(403, "没有权限！");
        }
    }
}
