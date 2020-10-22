package com.xzixi.framework.webapps.common.model.enums;

import com.xzixi.framework.boot.webmvc.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别
 *
 * @author 薛凌康
 */
@Getter
@AllArgsConstructor
public enum Sex implements IBaseEnum {

    /** 男 */
    MALE("男"),
    /** 女 */
    FEMALE("女");

    private final String value;
}
