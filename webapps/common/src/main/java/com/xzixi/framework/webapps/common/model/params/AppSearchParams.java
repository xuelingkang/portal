package com.xzixi.framework.webapps.common.model.params;

import com.xzixi.framework.boot.webmvc.model.search.BaseSearchParams;
import com.xzixi.framework.webapps.common.model.po.App;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author xuelingkang
 * @date 2020-10-25
 */
@Data
@ApiModel(description = "应用查询参数")
public class AppSearchParams extends BaseSearchParams<App> {
}
