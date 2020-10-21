package com.xzixi.framework.content.service;

import com.xzixi.framework.common.model.po.JobParameter;
import com.xzixi.framework.boot.webmvc.service.IBaseService;

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
