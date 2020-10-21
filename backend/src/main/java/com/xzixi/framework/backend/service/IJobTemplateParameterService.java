package com.xzixi.framework.backend.service;

import com.xzixi.framework.common.model.po.JobTemplateParameter;
import com.xzixi.framework.boot.webmvc.service.IBaseService;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
public interface IJobTemplateParameterService extends IBaseService<JobTemplateParameter> {

    /**
     * 根据任务模板id查询
     *
     * @param jobTemplateId 任务模板id
     * @return List&lt;JobTemplateParameter>
     */
    List<JobTemplateParameter> listByJobTemplateId(Integer jobTemplateId);

    /**
     * 根据任务模板id集合查询
     *
     * @param jobTemplateIds 任务模板id集合
     * @return List&lt;JobTemplateParameter>
     */
    List<JobTemplateParameter> listByJobTemplateIds(Collection<Integer> jobTemplateIds);
}
