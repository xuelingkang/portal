package com.xzixi.framework.backend.service.impl;

import com.xzixi.framework.backend.data.IJobParameterData;
import com.xzixi.framework.backend.model.po.JobParameter;
import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.backend.service.IJobParameterService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
@Service
public class JobParameterServiceImpl extends BaseServiceImpl<IJobParameterData, JobParameter> implements IJobParameterService {

    @Override
    public List<JobParameter> listByJobId(Integer jobId) {
        return list(new QueryParams<>(new JobParameter().setJobId(jobId)));
    }

    @Override
    public List<JobParameter> listByJobIds(Collection<Integer> jobIds) {
        return list(new QueryParams<JobParameter>().in("jobId", jobIds));
    }
}