package com.xzixi.self.portal.webapp.constant;

/**
 * @author 薛凌康
 */
public interface Swagger2Constant {

    String AUTHORIZATION_API_FILTER_NAME = "swaggerFilter";
    String AUTHORIZATION_PARAMETER_NAME = "Authorization";
    String AUTHORIZATION_PARAMETER_TYPE = "header";
    String AUTHORIZATION_EXCLUDE_URL_REG = "^(?!/login).*$";
    String AUTHORIZATION_SCOPE_NAME = "global";
    String AUTHORIZATION_SCOPE_DESCRIPTION = "全局";
}
