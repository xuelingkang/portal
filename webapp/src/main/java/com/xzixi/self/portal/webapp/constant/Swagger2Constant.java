package com.xzixi.self.portal.webapp.constant;

/**
 * @author 薛凌康
 */
public interface Swagger2Constant {

    String AUTHENTICATION_API_FILTER_NAME = "swaggerFilter";
    String AUTHENTICATION_PARAMETER_NAME = "Authorization";
    String AUTHENTICATION_PARAMETER_TYPE = "header";
    String AUTHENTICATION_EXCLUDE_URL_REG = "^(?!(/login|/website/user|/user/reset-password)).*$";
    String AUTHENTICATION_SCOPE_NAME = "global";
    String AUTHENTICATION_SCOPE_DESCRIPTION = "全局";
    String AUTHENTICATION_TEMPLATE = "/authentication.json";
}
