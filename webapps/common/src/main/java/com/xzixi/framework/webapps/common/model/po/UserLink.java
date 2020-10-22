package com.xzixi.framework.webapps.common.model.po;

import com.xzixi.framework.boot.webmvc.model.BaseModel;
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

    /** 关注时间 */
    private Long followTime;

    public UserLink(Integer idolId, Integer followerId) {
        this.idolId = idolId;
        this.followerId = followerId;
    }
}
