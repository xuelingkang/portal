package com.xzixi.self.portal.webapp.base.util;

import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.config.security.UserDetailsImpl;
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
}
