package com.xzixi.self.portal.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.webapp.data.IJobTemplateParameterData;
import com.xzixi.self.portal.webapp.framework.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.model.po.JobTemplateParameter;
import com.xzixi.self.portal.webapp.service.IJobTemplateParameterService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
@Service
public class JobTemplateParameterServiceImpl extends BaseServiceImpl<IJobTemplateParameterData, JobTemplateParameter> implements IJobTemplateParameterService {

    @Override
    public List<JobTemplateParameter> listByJobTemplateIds(Collection<Integer> jobTemplateIds) {
        return list(new QueryWrapper<JobTemplateParameter>().in("job_template_id", jobTemplateIds));
    }
}
