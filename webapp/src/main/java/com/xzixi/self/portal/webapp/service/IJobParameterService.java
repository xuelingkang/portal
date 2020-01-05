package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.framework.service.IBaseService;
import com.xzixi.self.portal.webapp.model.po.JobParameter;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
public interface IJobParameterService extends IBaseService<JobParameter> {

    /**
     * 根据定时任务id查询
     *
     * @param jobId 定时任务id
     * @return List&lt;JobParameter>
     */
    List<JobParameter> listByJobId(Integer jobId);

    /**
     * 根据定时任务id集合查询
     *
     * @param jobIds 定时任务id集合
     * @return List&lt;JobParameter>
     */
    List<JobParameter> listByJobIds(Collection<Integer> jobIds);
}