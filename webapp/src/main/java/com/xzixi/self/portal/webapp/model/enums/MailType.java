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

    /** 公开的 */
    PUBLIC("公开的"),
    /** 私人的 */
    PRIVATE("私人的");

    private String value;
}
