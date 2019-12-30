package com.xzixi.self.portal.webapp.model.params;

import com.xzixi.self.portal.webapp.framework.model.BaseSearchParams;
import com.xzixi.self.portal.webapp.framework.model.annotation.Like;
import com.xzixi.self.portal.webapp.model.po.Authority;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "权限查询参数")
public class AuthoritySearchParams extends BaseSearchParams<Authority> {

    /**
     * TODO 测试用，用完删除
     */
    @Like("pattern")
    private String patternLike;
}
