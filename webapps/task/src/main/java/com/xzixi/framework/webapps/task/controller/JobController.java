/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.task.controller;

import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.boot.core.model.search.Pagination;
import com.xzixi.framework.boot.core.util.BeanUtils;
import com.xzixi.framework.webapps.common.model.params.JobSearchParams;
import com.xzixi.framework.webapps.common.model.po.Job;
import com.xzixi.framework.webapps.common.model.valid.JobSave;
import com.xzixi.framework.webapps.common.model.valid.JobUpdate;
import com.xzixi.framework.webapps.common.model.vo.JobVO;
import com.xzixi.framework.webapps.task.service.IJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.xzixi.framework.webapps.common.constant.ProjectConstant.RESPONSE_MEDIA_TYPE;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/job", produces = RESPONSE_MEDIA_TYPE)
@Api(tags = "定时任务")
@Validated
public class JobController {

    @Autowired
    private IJobService jobService;

    @GetMapping
    @ApiOperation(value = "分页查询定时任务")
    public Result<Pagination<JobVO>> page(JobSearchParams searchParams) {
        searchParams.setDefaultOrders("id asc");
        Pagination<Job> jobPage = jobService.page(searchParams.buildPagination(), searchParams.buildQueryParams());
        Pagination<JobVO> page = jobService.buildVO(jobPage, new JobVO.BuildOption(true, false));
        return new Result<>(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询定时任务")
    public Result<JobVO> getById(
            @ApiParam(value = "定时任务id", required = true) @NotNull(message = "定时任务id不能为空！") @PathVariable Integer id) {
        JobVO jobVO = jobService.buildVO(id, new JobVO.BuildOption(true, true));
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
        Job jobData = jobService.getById(jobVO.getId());
        jobData.setEndTime(jobVO.getEndTime());
        BeanUtils.copyPropertiesIgnoreNull(jobVO, jobData);
        jobService.updateJob(jobData, jobVO.getParameters());
        return new Result<>();
    }

    @DeleteMapping
    @ApiOperation("删除定时任务")
    public Result<?> remove(
            @ApiParam(value = "定时任务id", required = true) @NotEmpty(message = "定时任务id不能为空！") @RequestParam List<Integer> ids) {
        jobService.removeJobsByIds(ids);
        return new Result<>();
    }

    @PatchMapping("/pause")
    @ApiOperation(value = "暂停定时任务")
    public Result<?> pause(
            @ApiParam(value = "定时任务id", required = true) @NotEmpty(message = "定时任务id不能为空！") @RequestParam List<Integer> ids) {
        jobService.pauseByIds(ids);
        return new Result<>();
    }

    @PatchMapping("/resume")
    @ApiOperation(value = "恢复定时任务")
    public Result<?> resume(
            @ApiParam(value = "定时任务id", required = true) @NotEmpty(message = "定时任务id不能为空！") @RequestParam List<Integer> ids) {
        jobService.resumeByIds(ids);
        return new Result<>();
    }
}
