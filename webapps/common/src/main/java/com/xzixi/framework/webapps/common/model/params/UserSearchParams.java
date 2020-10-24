package com.xzixi.framework.webapps.common.model.params;

import com.xzixi.framework.boot.webmvc.model.search.BaseSearchParams;
import com.xzixi.framework.webapps.common.model.po.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "用户查询参数")
public class UserSearchParams extends BaseSearchParams<User> {
}