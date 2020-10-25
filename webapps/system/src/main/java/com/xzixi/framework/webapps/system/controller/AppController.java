package com.xzixi.framework.webapps.system.controller;

import com.xzixi.framework.boot.webmvc.exception.ServerException;
import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.boot.webmvc.model.search.Pagination;
import com.xzixi.framework.boot.webmvc.util.BeanUtils;
import com.xzixi.framework.webapps.common.model.params.AppSearchParams;
import com.xzixi.framework.webapps.common.model.po.App;
import com.xzixi.framework.webapps.common.model.valid.AppSave;
import com.xzixi.framework.webapps.common.model.valid.AppUpdate;
import com.xzixi.framework.webapps.system.service.IAppService;
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
 * 应用 前端控制器
 *
 * @author xuelingkang
 * @date 2020-10-25
 */
@RestController
@RequestMapping(value = "/app", produces = RESPONSE_MEDIA_TYPE)
@Api(tags = "应用")
@Validated
public class AppController {

    @Autowired
    private IAppService appService;

    @GetMapping
    @ApiOperation(value = "分页查询应用")
    public Result<Pagination<App>> page(AppSearchParams searchParams) {
        searchParams.setDefaultOrders("seq asc");
        Pagination<App> page = appService.page(searchParams.buildPagination(), searchParams.buildQueryParams());
        return new Result<>(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询应用")
    public Result<App> getById(
            @ApiParam(value = "应用id", required = true) @NotNull(message = "应用id不能为空！") @PathVariable Integer id) {
        App app = appService.getById(id);
        return new Result<>(app);
    }

    @GetMapping("/uid/{uid}")
    @ApiOperation(value = "根据uid查询应用")
    public Result<App> getByUid(
            @ApiParam(value = "应用uid", required = true) @NotNull(message = "应用uid不能为空！") @PathVariable String uid) {
        App app = appService.getByUid(uid);
        return new Result<>(app);
    }

    @PostMapping
    @ApiOperation(value = "保存应用")
    public Result<?> save(@Validated({AppSave.class}) App app) {
        if (appService.save(app)) {
            return new Result<>();
        }
        throw new ServerException(app, "保存应用失败！");
    }

    @PutMapping
    @ApiOperation(value = "更新应用")
    public Result<?> update(@Validated({AppUpdate.class}) App app) {
        App appData = appService.getById(app.getId());
        BeanUtils.copyProperties(app, appData);
        if (appService.updateById(appData)) {
            return new Result<>();
        }
        throw new ServerException(app, "更新应用失败！");
    }

    @DeleteMapping
    @ApiOperation(value = "删除应用")
    public Result<?> remove(@ApiParam(value = "应用id", required = true) @NotEmpty(message = "应用id不能为空！") @RequestParam List<Integer> ids) {
        appService.removeByIds(ids);
        return new Result<>();
    }
}
