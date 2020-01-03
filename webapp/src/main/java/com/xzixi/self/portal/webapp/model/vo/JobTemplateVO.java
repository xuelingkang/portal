package com.xzixi.self.portal.webapp.model.vo;

import com.xzixi.self.portal.framework.util.BeanUtils;
import com.xzixi.self.portal.webapp.model.po.JobTemplate;
import com.xzixi.self.portal.webapp.model.po.JobTemplateParameter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Collection;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "任务模板")
public class JobTemplateVO extends JobTemplate {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务模板参数列表")
    private Collection<JobTemplateParameter> parameters;

    public JobTemplateVO(JobTemplate jobTemplate, String... ignoreProperties) {
        BeanUtils.copyProperties(jobTemplate, this, ignoreProperties);
    }

    @Getter
    @AllArgsConstructor
    public static class BuildOption {
        private boolean parameters;
    }
}
