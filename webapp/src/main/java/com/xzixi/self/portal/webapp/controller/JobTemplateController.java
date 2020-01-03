package com.xzixi.self.portal.webapp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzixi.self.portal.framework.model.Result;
import com.xzixi.self.portal.webapp.model.params.JobTemplateSearchParams;
import com.xzixi.self.portal.webapp.model.po.JobTemplate;
import com.xzixi.self.portal.webapp.model.valid.JobTemplateSave;
import com.xzixi.self.portal.webapp.model.valid.JobTemplateUpdate;
import com.xzixi.self.portal.webapp.model.vo.JobTemplateVO;
import com.xzixi.self.portal.webapp.service.IJobTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/job-template", produces = "application/json; charset=UTF-8")
@Api(tags = "任务模板")
@Validated
public class JobTemplateController {

    @Autowired
    private IJobTemplateService jobTemplateService;

    @GetMapping
    @ApiOperation(value = "分页查询任务模板")
    public Result<IPage<JobTemplate>> page(JobTemplateSearchParams searchParams) {
        searchParams.setDefaultOrderItems(new String[]{"id asc"});
        IPage<JobTemplate> page = jobTemplateService.page(searchParams.buildPageParams(), searchParams.buildQueryWrapper());
        return new Result<>(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询任务模板")
    public Result<JobTemplateVO> getById(
            @ApiParam(value = "任务模板id", required = true) @NotNull(message = "任务模板id不能为空！") @PathVariable Integer id) {
        JobTemplateVO jobTemplateVO = jobTemplateService.buildJobTemplateVO(id);
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
