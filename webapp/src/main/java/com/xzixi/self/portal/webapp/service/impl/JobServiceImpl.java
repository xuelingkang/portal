package com.xzixi.self.portal.webapp.service.impl;

import com.xzixi.self.portal.webapp.data.IJobData;
import com.xzixi.self.portal.webapp.framework.exception.ProjectException;
import com.xzixi.self.portal.webapp.framework.exception.ServerException;
import com.xzixi.self.portal.webapp.framework.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.model.po.Job;
import com.xzixi.self.portal.webapp.model.po.JobParameter;
import com.xzixi.self.portal.webapp.model.po.JobTemplate;
import com.xzixi.self.portal.webapp.model.po.JobTrigger;
import com.xzixi.self.portal.webapp.model.vo.JobVO;
import com.xzixi.self.portal.webapp.service.IJobParameterService;
import com.xzixi.self.portal.webapp.service.IJobService;
import com.xzixi.self.portal.webapp.service.IJobTemplateService;
import com.xzixi.self.portal.webapp.service.IJobTriggerService;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.xzixi.self.portal.webapp.framework.constant.JobConstant.JOB_PREFIX;
import static com.xzixi.self.portal.webapp.framework.constant.JobConstant.TRIGGER_PREFIX;

/**
 * @author 薛凌康
 */
@Service
public class JobServiceImpl extends BaseServiceImpl<IJobData, Job> implements IJobService {

    @Autowired
    private IJobParameterService jobParameterService;
    @Autowired
    private IJobTriggerService jobTriggerService;
    @Autowired
    private IJobTemplateService jobTemplateService;
    @Autowired
    private Scheduler scheduler;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveJob(Job job, Collection<JobParameter> parameters) {
        scheduleJob(job, parameters);
        if (!save(job)) {
            throw new ServerException("保存定时任务失败！");
        }
        parameters.forEach(parameter -> parameter.setJobId(job.getId()));
        if (!jobParameterService.saveBatch(parameters)) {
            throw new ServerException("保存定时任务参数失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJob(Job job, Collection<JobParameter> parameters) {
        if (!unscheduleJob(job)) {
            throw new ServerException("关闭定时任务失败！");
        }
        scheduleJob(job, parameters);
        if (!updateById(job)) {
            throw new ServerException("更新定时任务失败！");
        }
        parameters.forEach(parameter -> parameter.setJobId(job.getId()));
        List<JobParameter> oldParameters = jobParameterService.listByJobId(job.getId());
        boolean mergeResult = jobParameterService.merge(parameters, oldParameters, (sources, target) -> sources.stream()
                .filter(source -> source.getId() != null && source.getId().equals(target.getId())).findFirst().orElse(null));
        if (!mergeResult) {
            throw new ServerException("更新定时任务参数失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeJobsByIds(Collection<Integer> ids) {
        Collection<Job> jobs = listByIds(ids);
        if (!unscheduleJobs(jobs)) {
            throw new ServerException("关闭定时任务失败！");
        }
        if (!removeByIds(ids)) {
            throw new ServerException("删除定时任务失败！");
        }
        List<JobParameter> parameters = jobParameterService.listByJobIds(ids);
        List<Integer> jobParameterIds = parameters.stream().map(JobParameter::getId).collect(Collectors.toList());
        if (!jobParameterService.removeByIds(jobParameterIds)) {
            throw new ServerException("删除定时任务参数失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pauseByIds(Collection<Integer> ids) {
        Collection<Job> jobs = listByIds(ids);
        for (Job job : jobs) {
            try {
                scheduler.pauseTrigger(buildTriggerKey(job));
            } catch (SchedulerException e) {
                throw new ServerException(String.format("暂停定时任务(%s)失败！", job.getId()), e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resumeByIds(Collection<Integer> ids) {
        Collection<Job> jobs = listByIds(ids);
        for (Job job : jobs) {
            try {
                scheduler.resumeTrigger(buildTriggerKey(job));
            } catch (SchedulerException e) {
                throw new ServerException(String.format("恢复定时任务(%s)失败！", job.getId()), e);
            }
        }
    }

    @Override
    public JobVO buildJobVO(Integer id) {
        Job job = getById(id);
        return buildJobVO(job);
    }

    @Override
    public JobVO buildJobVO(Job job) {
        JobVO jobVO = new JobVO(job);
        JobTrigger jobTrigger = jobTriggerService.getByJob(job);
        jobVO.setJobTrigger(jobTrigger);
        List<JobParameter> parameters = jobParameterService.listByJobId(job.getId());
        jobVO.setParameters(parameters);
        return jobVO;
    }

    /**
     * 开启定时任务
     *
     * @param job 定时任务
     * @param parameters 定时任务参数
     */
    @SuppressWarnings("unchecked")
    private void scheduleJob(Job job, Collection<JobParameter> parameters) {
        // 任务模板
        JobTemplate jobTemplate = jobTemplateService.getById(job.getJobTemplateId());
        // 调度器名称
        String schedName;
        try {
            schedName = scheduler.getSchedulerName();
        } catch (SchedulerException e) {
            throw new ProjectException("获取任务调度器名称出错！", e);
        }
        // 名称
        String name = jobTemplate.getName() + "-" + UUID.randomUUID().toString();
        // 分组
        String group = jobTemplate.getClassName();
        // 任务名称
        String jobName = JOB_PREFIX + name;
        // 任务分组
        String jobGroup = JOB_PREFIX + group;
        // 触发器名称
        String triggerName = TRIGGER_PREFIX + name;
        // 触发器分组
        String triggerGroup = TRIGGER_PREFIX + group;
        // 开始时间
        Long startTime = job.getStartTime();
        // 结束时间
        Long endTime = job.getEndTime();
        // cron
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        // 创建任务
        // 任务类
        Class<? extends org.quartz.Job> jobClass;
        try {
            jobClass = (Class<? extends org.quartz.Job>) Class.forName(jobTemplate.getClassName());
        } catch (ClassNotFoundException e) {
            throw new ProjectException("获取任务类对象出错！", e);
        }
        JobDetail jobDetail = JobBuilder
                .newJob(jobClass)
                .withIdentity(jobName, jobGroup)
                .build();
        // 设置参数
        if (CollectionUtils.isNotEmpty(parameters)) {
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            parameters.forEach(parameter -> jobDataMap.put(parameter.getName(), parameter.getValue()));
        }
        // 创建任务触发器
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
        triggerBuilder.withIdentity(triggerName, triggerGroup).startAt(new Date(startTime));
        if (endTime!=null) {
            triggerBuilder.endAt(new Date(endTime));
        }
        Trigger trigger = triggerBuilder.withSchedule(scheduleBuilder).build();
        try {
            // 将触发器与任务绑定到调度器内
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new ProjectException("创建定时任务时出错！", e);
        }
        // 设置job属性
        job.setSchedName(schedName);
        job.setTriggerName(triggerName);
        job.setTriggerGroup(triggerGroup);
    }

    /**
     * 删除触发器，任务实例会自动删除
     *
     * @param job 定时任务
     */
    private boolean unscheduleJob(Job job) {
        try {
            return scheduler.unscheduleJob(buildTriggerKey(job));
        } catch (SchedulerException e) {
            throw new ProjectException("删除定时任务触发器时出错！", e);
        }
    }

    /**
     * 删除触发器，任务实例会自动删除
     *
     * @param jobs 定时任务集合
     */
    private boolean unscheduleJobs(Collection<Job> jobs) {
        List<TriggerKey> triggerKeys = buildTriggerKeys(jobs);
        try {
            return scheduler.unscheduleJobs(triggerKeys);
        } catch (SchedulerException e) {
            throw new ProjectException("删除定时任务触发器时出错！", e);
        }
    }

    private TriggerKey buildTriggerKey(Job job) {
        return new TriggerKey(job.getTriggerName(), job.getTriggerGroup());
    }

    private List<TriggerKey> buildTriggerKeys(Collection<Job> jobs) {
        return jobs.stream().map(this::buildTriggerKey).collect(Collectors.toList());
    }
}
