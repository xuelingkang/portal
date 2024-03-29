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

package com.xzixi.framework.webapps.remote.service;

import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.boot.core.model.search.Pagination;
import com.xzixi.framework.webapps.common.model.params.AppSearchParams;
import com.xzixi.framework.webapps.common.model.po.App;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xuelingkang
 * @date 2020-10-25
 */
@FeignClient(value = "portal-system", path = "/app", contextId = "app")
public interface RemoteAppService {

    @GetMapping
    Result<Pagination<App>> page(@SpringQueryMap AppSearchParams searchParams);

    @GetMapping("/{id}")
    Result<App> getById(@PathVariable("id") Integer id);

    @GetMapping("/uid/{uid}")
    Result<App> getByUid(@PathVariable("uid") String uid);

    @PostMapping
    Result<?> save(@RequestBody App app);

    @PutMapping
    Result<?> update(@RequestBody App app);

    @DeleteMapping
    Result<?> remove(@RequestParam("ids") List<Integer> ids);
}
