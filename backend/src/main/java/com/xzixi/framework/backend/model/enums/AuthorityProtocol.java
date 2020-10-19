package com.xzixi.framework.backend.model.enums;

import com.xzixi.framework.boot.webmvc.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 薛凌康
 */
@Getter
@AllArgsConstructor
public enum AuthorityProtocol implements IBaseEnum {

    /** http */
    HTTP("HTTP"),
    /** websocket */
    WEBSOCKET("WEBSOCKET");

    private final String value;
}
