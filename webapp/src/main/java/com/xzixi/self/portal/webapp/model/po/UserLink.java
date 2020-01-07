package com.xzixi.self.portal.webapp.model.po;

import com.xzixi.self.portal.framework.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关注
 *
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
public class UserLink extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /** 偶像id */
    private Integer idolId;

    /** 粉丝id */
    private Integer followerId;

    public UserLink(Integer idolId, Integer followerId) {
        this.idolId = idolId;
        this.followerId = followerId;
    }
}
