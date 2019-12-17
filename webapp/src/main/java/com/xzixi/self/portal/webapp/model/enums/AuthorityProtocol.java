package com.xzixi.self.portal.webapp.model.enums;

import com.xzixi.self.portal.webapp.model.IBaseEnum;
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

    private String value;
}
