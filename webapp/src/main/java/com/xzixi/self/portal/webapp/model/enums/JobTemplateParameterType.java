package com.xzixi.self.portal.webapp.model.enums;

import com.xzixi.self.portal.framework.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 薛凌康
 */
@Getter
@AllArgsConstructor
public enum JobTemplateParameterType implements IBaseEnum {

    /** int型整数 */
    INTEGER("int型整数"),
    /** long型整数 */
    LONG("long型整数"),
    /** 浮点型 */
    DOUBLE("浮点型"),
    /** 字符串 */
    STRING("字符串");

    private String value;
}
