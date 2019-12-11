package com.xzixi.self.portal.webapp.model.po;

import com.xzixi.self.portal.webapp.model.BaseModel;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
public class RoleAuthorityLink extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer roleId;
    private Integer authorityId;
}
