package com.xzixi.self.portal.webapp.model.vo;

import com.xzixi.self.portal.framework.webmvc.util.BeanUtils;
import com.xzixi.self.portal.webapp.model.po.Job;
import com.xzixi.self.portal.webapp.model.po.JobParameter;
import com.xzixi.self.portal.webapp.model.po.JobTrigger;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@ApiModel(description = "定时任务")
public class JobVO extends Job {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "触发器")
    private JobTrigger jobTrigger;

    @ApiModelProperty(value = "定时任务参数列表")
    private Collection<JobParameter> parameters;

    public JobVO(Job job, String... ignoreProperties) {
        BeanUtils.copyProperties(job, this, ignoreProperties);
    }

    @Getter
    @AllArgsConstructor
    public static class BuildOption {
        private boolean trigger;
        private boolean parameters;
    }
}
