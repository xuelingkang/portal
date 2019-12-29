package com.xzixi.self.portal.webapp.model.vo;

import com.xzixi.self.portal.webapp.model.po.JobTemplateParameter;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "任务模板参数")
public class JobTemplateParameterVO extends JobTemplateParameter {

    private static final long serialVersionUID = 1L;

    public JobTemplateParameterVO(JobTemplateParameter jobTemplateParameter, String... ignoreProperties) {
        BeanUtils.copyProperties(jobTemplateParameter, this, ignoreProperties);
    }
}
