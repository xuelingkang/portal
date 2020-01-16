package com.xzixi.self.portal.webapp.util;

import com.xzixi.self.portal.webapp.config.security.UserDetailsImpl;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author 薛凌康
 */
public class SecurityUtils {

    public static UserVO getCurrentUser() {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    public static Integer getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
