package com.xzixi.framework.webapp.model.po;

import com.xzixi.framework.boot.webmvc.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
public class UserRoleLink extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userId;

    private Integer roleId;

    public UserRoleLink(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
