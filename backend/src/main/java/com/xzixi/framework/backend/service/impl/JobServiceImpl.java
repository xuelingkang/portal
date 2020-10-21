package com.xzixi.framework.backend.service.impl;

import com.xzixi.framework.boot.webmvc.exception.ClientException;
import com.xzixi.framework.boot.webmvc.exception.ProjectException;
import com.xzixi.framework.boot.webmvc.exception.ServerException;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.common.constant.JobConstant;
import com.xzixi.framework.backend.data.IJobData;
import com.xzixi.framework.common.model.po.JobParameter;
import com.xzixi.framework.common.model.po.JobTemplate;
import com.xzixi.framework.common.model.po.JobTemplateParameter;
import com.xzixi.framework.common.model.po.JobTrigger;
import com.xzixi.framework.common.model.vo.JobVO;
import com.xzixi.framework.backend.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@Service
public class JobServiceImpl extends BaseServiceImpl<IJobData, com.xzixi.framework.common.model.po.Job> implements IJobService {

    @Autowired
    private IJobParameterService jobParameterService;
    @Autowired
    private IJobTriggerService jobTriggerService;
    @Autowired
    private IJobTemplateService jobTemplateService;
    @Autowired
    private IJobTemplateParameterService jobTemplateParameterService;
    @Autowired
    private Scheduler scheduler;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveJob(com.xzixi.framework.common.model.po.Job job, Collection<JobParameter> parameters) {
        checkParameters(job.getJobTemplateId(), parameters);
        scheduleJob(job, parameters);
        if (!save(job)) {
            throw new ServerException(job, "保存定时任务失败！");
        }
        parameters.forEach(parameter -> parameter.setJobId(job.getId()));
        if (!jobParameterService.defaultSaveBatch(parameters)) {
            throw new ServerException(parameters, "保存定时任务参数失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJob(com.xzixi.framework.common.model.po.Job job, Collection<JobParameter> parameters) {
        checkParameters(job.getJobTemplateId(), parameters);
        if (!unscheduleJob(job)) {
            throw new ServerException(job, "关闭定时任务失败！");
        }
        scheduleJob(job, parameters);
        if (!updateById(job)) {
            throw new ServerException(job, "更新定时任务失败！");
        }
        parameters.forEach(parameter -> parameter.setJobId(job.getId()));
        List<JobParameter> oldParameters = jobParameterService.listByJobId(job.getId());
        boolean mergeResult = jobParameterService.merge(parameters, oldParameters, (sources, target) -> sources.stream()
                .filter(source -> Objects.equals(source.getId(), target.getId())).findFirst().orElse(null));
        if (!mergeResult) {
            throw new ServerException(parameters, "更新定时任务参数失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeJobsByIds(Collection<Integer> ids) {
        Collection<com.xzixi.framework.common.model.po.Job> jobs = listByIds(ids);
        if (!unscheduleJobs(jobs)) {
            throw new ServerException(jobs, "关闭定时任务失败！");
        }
        if (!removeByIds(ids)) {
            throw new ServerException(jobs, "删除定时任务失败！");
        }
        List<JobParameter> parameters = jobParameterService.listByJobIds(ids);
        List<Integer> jobParameterIds = parameters.stream().map(JobParameter::getId).collect(Collectors.toList());
        if (!jobParameterService.removeByIds(jobParameterIds)) {
            throw new ServerException(parameters, "删除定时任务参数失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pauseByIds(Collection<Integer> ids) {
        Collection<com.xzixi.framework.common.model.po.Job> jobs = listByIds(ids);
        for (com.xzixi.framework.common.model.po.Job job : jobs) {
            try {
                scheduler.pauseTrigger(buildTriggerKey(job));
            } catch (SchedulerException e) {
                throw new ServerException(job, "暂停定时任务失败！", e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resumeByIds(Collection<Integer> ids) {
        Collection<com.xzixi.framework.common.model.po.Job> jobs = listByIds(ids);
        for (com.xzixi.framework.common.model.po.Job job : jobs) {
            try {
                scheduler.resumeTrigger(buildTriggerKey(job));
            } catch (SchedulerException e) {
                throw new ServerException(job, "恢复定时任务失败！", e);
            }
        }
    }

    @Override
    public JobVO buildVO(com.xzixi.framework.common.model.po.Job job, JobVO.BuildOption option) {
        JobVO jobVO = new JobVO(job);
        if (option.isTrigger()) {
            JobTrigger jobTrigger = jobTriggerService.getByJob(job);
            jobVO.setJobTrigger(jobTrigger);
        }
        if (option.isParameters()) {
            List<JobParameter> parameters = jobParameterService.listByJobId(job.getId());
            jobVO.setParameters(parameters);
        }
        return jobVO;
    }

    @Override
    public List<JobVO> buildVO(Collection<com.xzixi.framework.common.model.po.Job> jobs, JobVO.BuildOption option) {
        List<JobVO> jobVOList = jobs.stream().map(JobVO::new).collect(Collectors.toList());
        if (option.isTrigger()) {
            List<JobTrigger> jobTriggers = jobTriggerService.listByJobs(jobs);
            jobVOList.forEach(jobVO -> {
                JobTrigger jobTrigger = jobTriggers.stream().filter(trigger ->
                        Objects.equals(jobVO.getSchedName(), trigger.getSchedName())
                        && Objects.equals(jobVO.getTriggerName(), trigger.getTriggerName())
                        && Objects.equals(jobVO.getTriggerGroup(), trigger.getTriggerGroup()))
                        .findFirst().orElse(null);
                jobVO.setJobTrigger(jobTrigger);
            });
        }
        if (option.isParameters()) {
            List<JobParameter> parameters = jobParameterService.listByJobIds(jobs.stream().map(com.xzixi.framework.common.model.po.Job::getId).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(parameters)) {
                jobVOList.forEach(jobVO -> {
                    List<JobParameter> params = parameters.stream().filter(parameter -> Objects.equals(jobVO.getId(), parameter.getJobId()))
                            .collect(Collectors.toList());
                    jobVO.setParameters(params);
                });
            }
        }
        return jobVOList;
    }

    /**
     * 检查定时任务参数
     * @param jobTemplateId 任务模板id
     * @param parameters 定时任务参数
     */
    private void checkParameters(Integer jobTemplateId, Collection<JobParameter> parameters) {
        Collection<JobTemplateParameter> templateParameters = jobTemplateParameterService.listByJobTemplateId(jobTemplateId);
        if (CollectionUtils.isEmpty(templateParameters) && CollectionUtils.isNotEmpty(parameters)) {
            checkError("参数与模板不一致!");
        }
        if (CollectionUtils.isNotEmpty(templateParameters) && CollectionUtils.isEmpty(parameters)) {
            checkError("参数与模板不一致!");
        }
        if (CollectionUtils.isEmpty(templateParameters)) {
            return;
        }
        if (templateParameters.size() != parameters.size()) {
            checkError("参数与模板不一致!");
        }
        for (JobTemplateParameter templateParameter : templateParameters) {
            JobParameter parameter = parameters.stream()
                    .filter(param -> Objects.equals(param.getName(), templateParameter.getName()))
                    .findFirst().orElse(null);
            if (parameter == null) {
                checkError(String.format("缺少参数(%s)!", templateParameter.getName()));
            }
            if (!templateParameter.getType().match(parameter.getValue())) {
                checkError(String.format("参数类型错误(%s=%s)!", parameter.getName(), parameter.getValue()));
            }
        }
    }

    private void checkError(String message) {
        throw new ClientException(400, message);
    }

    /**
     * 开启定时任务
     *
     * @param job 定时任务
     * @param parameters 定时任务参数
     */
    @SuppressWarnings("unchecked")
    private void scheduleJob(com.xzixi.framework.common.model.po.Job job, Collection<JobParameter> parameters) {
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
        String jobName = JobConstant.JOB_PREFIX + name;
        // 任务分组
        String jobGroup = JobConstant.JOB_PREFIX + group;
        // 触发器名称
        String triggerName = JobConstant.TRIGGER_PREFIX + name;
        // 触发器分组
        String triggerGroup = JobConstant.TRIGGER_PREFIX + group;
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
    private boolean unscheduleJob(com.xzixi.framework.common.model.po.Job job) {
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
    private boolean unscheduleJobs(Collection<com.xzixi.framework.common.model.po.Job> jobs) {
        List<TriggerKey> triggerKeys = buildTriggerKeys(jobs);
        try {
            return scheduler.unscheduleJobs(triggerKeys);
        } catch (SchedulerException e) {
            throw new ProjectException("删除定时任务触发器时出错！", e);
        }
    }

    private TriggerKey buildTriggerKey(com.xzixi.framework.common.model.po.Job job) {
        return new TriggerKey(job.getTriggerName(), job.getTriggerGroup());
    }

    private List<TriggerKey> buildTriggerKeys(Collection<com.xzixi.framework.common.model.po.Job> jobs) {
        return jobs.stream().map(this::buildTriggerKey).collect(Collectors.toList());
    }
}
