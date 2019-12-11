package com.xzixi.self.portal.webapp.model.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 薛凌康
 */
@Data
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tokenStr;
    private Integer userId;
    private Long loginTime;
    private Long expireTime;
}
