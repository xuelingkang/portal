package com.xzixi.framework.webapps.common.feign;

import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.boot.webmvc.model.search.Pagination;
import com.xzixi.framework.webapps.common.model.params.AppSearchParams;
import com.xzixi.framework.webapps.common.model.po.App;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xuelingkang
 * @date 2020-10-25
 */
@FeignClient(value = "portal-system", path = "/app")
public interface RemoteAppService {

    @GetMapping
    Result<Pagination<App>> page(AppSearchParams searchParams);

    @GetMapping("/{id}")
    Result<App> getById(@PathVariable Integer id);

    @GetMapping("/uid/{uid}")
    Result<App> getByUid(@PathVariable String uid);

    @PostMapping
    Result<?> save(App app);

    @PutMapping
    Result<?> update(App app);

    @DeleteMapping
    Result<?> remove(List<Integer> ids);
}
