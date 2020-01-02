package com.xzixi.self.portal.webapp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzixi.self.portal.webapp.framework.model.Result;
import com.xzixi.self.portal.webapp.model.params.JobSearchParams;
import com.xzixi.self.portal.webapp.model.po.Job;
import com.xzixi.self.portal.webapp.model.po.JobTrigger;
import com.xzixi.self.portal.webapp.model.valid.JobSave;
import com.xzixi.self.portal.webapp.model.valid.JobUpdate;
import com.xzixi.self.portal.webapp.model.vo.JobVO;
import com.xzixi.self.portal.webapp.service.IJobService;
import com.xzixi.self.portal.webapp.service.IJobTriggerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/job", produces = "application/json; charset=UTF-8")
@Api(tags = "定时任务")
@Validated
public class JobController {

    @Autowired
    private IJobService jobService;
    @Autowired
    private IJobTriggerService jobTriggerService;

    @GetMapping
    @ApiOperation(value = "分页查询定时任务")
    public Result<IPage<JobVO>> page(JobSearchParams searchParams) {
        searchParams.setDefaultOrderItems(new String[]{"id asc"});
        IPage<Job> jobPage = jobService.page(searchParams.buildPageParams(), searchParams.buildQueryWrapper());
        List<Job> jobs = jobPage.getRecords();
        List<JobTrigger> jobTriggers = jobTriggerService.listByJobs(jobs);
        List<JobVO> jobVOList = jobs.stream().map(job -> {
            JobTrigger jobTrigger = jobTriggers.stream().filter(trigger -> job.getSchedName().equals(trigger.getSchedName())
                    && job.getTriggerName().equals(trigger.getTriggerName()) && job.getTriggerGroup().equals(trigger.getTriggerGroup()))
                    .findFirst().orElse(null);
            return new JobVO(job).setJobTrigger(jobTrigger);
        }).collect(Collectors.toList());
        IPage<JobVO> page = new Page<>(jobPage.getCurrent(), jobPage.getSize(), jobPage.getTotal(), jobPage.isSearchCount());
        page.setRecords(jobVOList);
        return new Result<>(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询定时任务")
    public Result<JobVO> getById(
            @ApiParam(value = "定时任务id", required = true) @NotNull(message = "定时任务id不能为空！") @PathVariable Integer id) {
        JobVO jobVO = jobService.buildJobVO(id);
        return new Result<>(jobVO);
    }

    @PostMapping
    @ApiOperation(value = "保存定时任务")
    public Result<?> save(@Validated({JobSave.class}) @RequestBody JobVO jobVO) {
        jobService.saveJob(jobVO, jobVO.getParameters());
        return new Result<>();
    }

    @PutMapping
    @ApiOperation("更新定时任务")
    public Result<?> update(@Validated({JobUpdate.class}) @RequestBody JobVO jobVO) {
        jobService.updateJob(jobVO, jobVO.getParameters());
        return new Result<>();
    }

    @DeleteMapping
    @ApiOperation("删除定时任务")
    public Result<?> remove(@ApiParam(value = "定时任务id", required = true) @NotEmpty(message = "定时任务id不能为空！") @RequestParam List<Integer> ids) {
        jobService.removeJobsByIds(ids);
        return new Result<>();
    }

    @PatchMapping("/pause")
    @ApiOperation(value = "暂停定时任务")
    public Result<?> pause(@ApiParam(value = "定时任务id", required = true) @NotEmpty(message = "定时任务id不能为空！") @RequestParam List<Integer> ids) {
        jobService.pauseByIds(ids);
        return new Result<>();
    }

    @PatchMapping("/resume")
    @ApiOperation(value = "恢复定时任务")
    public Result<?> resume(@ApiParam(value = "定时任务id", required = true) @NotEmpty(message = "定时任务id不能为空！") @RequestParam List<Integer> ids) {
        jobService.resumeByIds(ids);
        return new Result<>();
    }
}
