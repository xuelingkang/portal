package com.xzixi.self.portal.webapp.model.po;

import com.xzixi.self.portal.framework.webmvc.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
public class RoleAuthorityLink extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer roleId;

    private Integer authorityId;

    public RoleAuthorityLink(Integer roleId, Integer authorityId) {
        this.roleId = roleId;
        this.authorityId = authorityId;
    }
}
