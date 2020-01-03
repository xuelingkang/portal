package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.framework.service.IBaseService;
import com.xzixi.self.portal.webapp.model.po.Job;
import com.xzixi.self.portal.webapp.model.po.JobParameter;
import com.xzixi.self.portal.webapp.model.vo.JobVO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
public interface IJobService extends IBaseService<Job> {

    /**
     * 保存定时任务
     *
     * @param job 定时任务
     * @param parameters 定时任务参数
     */
    void saveJob(Job job, Collection<JobParameter> parameters);

    /**
     * 更新定时任务
     *
     * @param job 定时任务
     * @param parameters 定时任务参数
     */
    void updateJob(Job job, Collection<JobParameter> parameters);

    /**
     * 根据id删除定时任务，同时删除关联的参数
     *
     * @param ids 定时任务id
     */
    void removeJobsByIds(Collection<Integer> ids);

    /**
     * 根据id暂停定时任务
     *
     * @param ids 定时任务id集合
     */
    void pauseByIds(Collection<Integer> ids);

    /**
     * 根据id恢复定时任务
     *
     * @param ids 定时任务id集合
     */
    void resumeByIds(Collection<Integer> ids);

    /**
     * 构建JobVO
     *
     * @param id 定时任务id
     * @param option 选项
     * @return JobVO
     */
    JobVO buildJobVO(Integer id, BuildOption option);

    /**
     * 构建JobVO
     *
     * @param job 定时任务
     * @param option 选项
     * @return JobVO
     */
    JobVO buildJobVO(Job job, BuildOption option);

    /**
     * 构建JobVO
     *
     * @param jobs 定时任务集合
     * @param option 选项
     * @return List&lt;JobVO>
     */
    List<JobVO> buildJobVO(Collection<Job> jobs, BuildOption option);

    @Data
    @AllArgsConstructor
    class BuildOption {
        private boolean trigger;
        private boolean parameters;
    }
}
