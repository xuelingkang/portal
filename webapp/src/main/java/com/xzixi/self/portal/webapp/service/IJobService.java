package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.webapp.framework.service.IBaseService;
import com.xzixi.self.portal.webapp.model.po.Job;
import com.xzixi.self.portal.webapp.model.vo.JobVO;

/**
 * @author 薛凌康
 */
public interface IJobService extends IBaseService<Job> {

    /**
     * 构建JobVO
     *
     * @param id 定时任务id
     * @return JobVO
     */
    JobVO buildJobVO(Integer id);

    /**
     * 构建JobVO
     *
     * @param job 定时任务
     * @return JobVO
     */
    JobVO buildJobVO(Job job);
}
