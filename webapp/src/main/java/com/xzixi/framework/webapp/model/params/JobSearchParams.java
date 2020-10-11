package com.xzixi.framework.webapp.model.params;

import com.xzixi.framework.boot.webmvc.model.search.BaseSearchParams;
import com.xzixi.framework.webapp.model.po.Job;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "定时任务查询参数")
public class JobSearchParams extends BaseSearchParams<Job> {
}
