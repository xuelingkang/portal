package com.xzixi.self.portal.webapp.framework.constant;

/**
 * @author 薛凌康
 */
public interface SecurityConstant {

    String AUTHENTICATION_HEADER_NAME = "x-access-token";
    String AUTHENTICATION_PARAMETER_NAME = "_access";
    long AUTHENTICATION_EXPIRE_SECOND = 7 * 24 * 60 * 60L;
    String RESET_PASSWORD_URL_PREFIX = "";
    long RESET_PASSWORD_URL_EXPIRE_SECOND = 30 * 60L;
    String AUTHENTICATION_JWT_SECRET = "S!E@L#F$P%O^R&T*A(L";
    String AUTHENTICATION_REDIS_KEY_PREFIX = "tokens:";
}
