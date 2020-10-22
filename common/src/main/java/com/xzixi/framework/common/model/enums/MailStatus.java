package com.xzixi.framework.common.model.enums;

import com.xzixi.framework.boot.webmvc.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 薛凌康
 */
@Getter
@AllArgsConstructor
public enum MailStatus implements IBaseEnum {

    /** 未发送 */
    UNSENT("未发送"),
    /** 成功 */
    SUCCESS("成功"),
    /** 失败 */
    FAILURE("失败");

    private final String value;
}