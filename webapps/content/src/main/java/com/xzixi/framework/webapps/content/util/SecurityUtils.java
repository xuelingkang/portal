package com.xzixi.framework.webapps.content.util;

import com.xzixi.framework.webapps.common.model.vo.UserVO;
import com.xzixi.framework.webapps.content.config.security.UserDetailsImpl;
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
