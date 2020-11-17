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
import com.xzixi.framework.webapps.common.model.params.JobTemplateSearchParams;
import com.xzixi.framework.webapps.common.model.po.JobTemplate;
import com.xzixi.framework.webapps.common.model.valid.JobTemplateSave;
import com.xzixi.framework.webapps.common.model.valid.JobTemplateUpdate;
import com.xzixi.framework.webapps.common.model.vo.JobTemplateVO;
import com.xzixi.framework.webapps.task.service.IJobTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.xzixi.framework.webapps.common.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/job-template", produces = RESPONSE_MEDIA_TYPE)
@Api(tags = "任务模板")
@Validated
public class JobTemplateController {

    @Autowired
    private IJobTemplateService jobTemplateService;

    @GetMapping
    @ApiOperation(value = "分页查询任务模板")
    public Result<Pagination<JobTemplateVO>> page(JobTemplateSearchParams searchParams) {
        searchParams.setDefaultOrders("id asc");
        Pagination<JobTemplate> jobTemplatePage = jobTemplateService.page(searchParams.buildPagination(), searchParams.buildQueryParams());
        Pagination<JobTemplateVO> page = jobTemplateService.buildVO(jobTemplatePage, new JobTemplateVO.BuildOption(false));
        return new Result<>(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询任务模板")
    public Result<JobTemplateVO> getById(
            @ApiParam(value = "任务模板id", required = true) @NotNull(message = "任务模板id不能为空！") @PathVariable Integer id) {
        JobTemplateVO jobTemplateVO = jobTemplateService.buildVO(id, new JobTemplateVO.BuildOption(true));
        return new Result<>(jobTemplateVO);
    }

    @PostMapping
    @ApiOperation(value = "保存任务模板")
    public Result<?> save(@Validated({JobTemplateSave.class}) @RequestBody JobTemplateVO jobTemplateVO) {
        jobTemplateService.saveJobTemplate(jobTemplateVO, jobTemplateVO.getParameters());
        return new Result<>();
    }

    @PutMapping
    @ApiOperation(value = "更新任务模板")
    public Result<?> update(@Validated({JobTemplateUpdate.class}) @RequestBody JobTemplateVO jobTemplateVO) {
        jobTemplateService.updateJobTemplateById(jobTemplateVO, jobTemplateVO.getParameters());
        return new Result<>();
    }

    @DeleteMapping
    @ApiOperation(value = "删除任务模板")
    public Result<?> remove(
            @ApiParam(value = "任务模板id", required = true) @NotEmpty(message = "任务模板id不能为空！") @RequestParam List<Integer> ids) {
        jobTemplateService.removeJobTemplatesByIds(ids);
        return new Result<>();
    }
}
