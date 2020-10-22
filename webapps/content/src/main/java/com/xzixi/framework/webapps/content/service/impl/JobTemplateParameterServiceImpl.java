package com.xzixi.framework.webapps.content.service.impl;

import com.xzixi.framework.webapps.content.data.IJobTemplateParameterData;
import com.xzixi.framework.webapps.common.model.po.JobTemplateParameter;
import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapps.content.service.IJobTemplateParameterService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
@Service
public class JobTemplateParameterServiceImpl extends BaseServiceImpl<IJobTemplateParameterData, JobTemplateParameter> implements IJobTemplateParameterService {

    @Override
    public List<JobTemplateParameter> listByJobTemplateId(Integer jobTemplateId) {
        return list(new QueryParams<>(new JobTemplateParameter().setJobTemplateId(jobTemplateId)));
    }

    @Override
    public List<JobTemplateParameter> listByJobTemplateIds(Collection<Integer> jobTemplateIds) {
        return list(new QueryParams<JobTemplateParameter>().in("jobTemplateId", jobTemplateIds));
    }
}
