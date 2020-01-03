package com.xzixi.self.portal.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.framework.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.data.IJobParameterData;
import com.xzixi.self.portal.webapp.model.po.JobParameter;
import com.xzixi.self.portal.webapp.service.IJobParameterService;
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
        return list(new QueryWrapper<>(new JobParameter().setJobId(jobId)));
    }

    @Override
    public List<JobParameter> listByJobIds(Collection<Integer> jobIds) {
        return list(new QueryWrapper<JobParameter>().in("job_id", jobIds));
    }
}
