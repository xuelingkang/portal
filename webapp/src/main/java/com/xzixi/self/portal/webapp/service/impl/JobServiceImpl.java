package com.xzixi.self.portal.webapp.service.impl;

import com.xzixi.self.portal.webapp.data.IJobData;
import com.xzixi.self.portal.webapp.data.IJobTriggerData;
import com.xzixi.self.portal.webapp.framework.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.model.po.Job;
import com.xzixi.self.portal.webapp.model.po.JobParameter;
import com.xzixi.self.portal.webapp.model.po.JobTrigger;
import com.xzixi.self.portal.webapp.model.vo.JobVO;
import com.xzixi.self.portal.webapp.service.IJobParameterService;
import com.xzixi.self.portal.webapp.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 薛凌康
 */
@Service
public class JobServiceImpl extends BaseServiceImpl<IJobData, Job> implements IJobService {

    @Autowired
    private IJobParameterService jobParameterService;
    @Autowired
    private IJobTriggerData jobTriggerData;

    @Override
    public JobVO buildJobVO(Integer id) {
        Job job = getById(id);
        return buildJobVO(job);
    }

    @Override
    public JobVO buildJobVO(Job job) {
        JobVO jobVO = new JobVO(job);
        JobTrigger jobTrigger = jobTriggerData.getByJob(job);
        jobVO.setJobTrigger(jobTrigger);
        List<JobParameter> parameters = jobParameterService.listByJobId(job.getId());
        jobVO.setParameters(parameters);
        return jobVO;
    }
}
