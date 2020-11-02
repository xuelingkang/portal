/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
