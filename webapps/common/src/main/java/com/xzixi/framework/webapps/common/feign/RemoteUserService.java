package com.xzixi.framework.webapps.common.feign;

import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.boot.webmvc.model.search.Pagination;
import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import com.xzixi.framework.webapps.common.model.params.UserSearchParams;
import com.xzixi.framework.webapps.common.model.po.User;
import com.xzixi.framework.webapps.common.model.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xuelingkang
 * @date 2020-10-25
 */
@FeignClient(value = "portal-system", path = "/user")
public interface RemoteUserService {

    @GetMapping("/page")
    Result<Pagination<UserVO>> page(UserSearchParams searchParams);

    @GetMapping("/one")
    Result<User> getOne(QueryParams<User> queryParams);

    @GetMapping("/{id}")
    Result<UserVO> getById(@PathVariable Integer id);

    @PatchMapping("/login-time")
    Result<?> updateLoginTime(Integer id, Long loginTime);
}
