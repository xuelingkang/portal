package com.xzixi.self.portal.webapp.model.enums;

import com.xzixi.self.portal.webapp.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求方法
 *
 * @author 薛凌康
 */
@Getter
@AllArgsConstructor
public enum AuthorityMethod implements IBaseEnum {

    /** http get请求 */
    GET("GET请求"),
    /** http head请求 */
    HEAD("HEAD请求"),
    /** http delete请求 */
    DELETE("DELETE请求"),
    /** http post请求 */
    POST("POST请求"),
    /** http put请求 */
    PUT("PUT请求"),
    /** http patch请求 */
    PATCH("PATCH请求"),
    /** stomp subscribe请求 */
    SUBSCRIBE("SUBSCRIBE请求");

    private String value;
}
