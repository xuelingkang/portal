package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.webapp.framework.service.IBaseService;
import com.xzixi.self.portal.webapp.model.po.JobTemplateParameter;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
public interface IJobTemplateParameterService extends IBaseService<JobTemplateParameter> {

    /**
     * 根据任务模板id集合查询
     *
     * @param jobTemplateIds 任务模板id集合
     * @return List&lt;JobTemplateParameter>
     */
    List<JobTemplateParameter> listByJobTemplateIds(Collection<Integer> jobTemplateIds);
}
