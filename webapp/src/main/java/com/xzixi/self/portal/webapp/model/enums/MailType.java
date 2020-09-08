package com.xzixi.self.portal.webapp.model.enums;

import com.xzixi.self.portal.framework.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 薛凌康
 */
@Getter
@AllArgsConstructor
public enum MailType implements IBaseEnum {

    /** 公开 */
    PUBLIC("公开"),
    /** 私人 */
    PRIVATE("个人");

    private final String value;
}
