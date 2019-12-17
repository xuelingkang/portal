package com.xzixi.self.portal.webapp.base.constant;

/**
 * @author 薛凌康
 */
public interface SecurityConstant {

    String AUTHENTICATION_HEADER_NAME = "x-access-token";
    String AUTHENTICATION_PARAMETER_NAME = "_access";
    long AUTHENTICATION_EXPIRE_SECOND = 7 * 24 * 60 * 60L;
    String AUTHENTICATION_JWT_SECRET = "S!E@L#F$P%O^R&T*A(L";
    String AUTHENTICATION_REDIS_KEY_PREFIX = "tokens:";
}
