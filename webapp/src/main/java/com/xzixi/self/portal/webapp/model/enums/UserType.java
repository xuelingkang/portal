package com.xzixi.self.portal.webapp.model.enums;

import com.xzixi.self.portal.framework.webmvc.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型
 *
 * @author 薛凌康
 */
@Getter
@AllArgsConstructor
public enum UserType implements IBaseEnum {

    /** 系统用户 */
    SYSTEM("系统用户"),
    /** 网站用户 */
    WEBSITE("网站用户");

    private final String value;
}
