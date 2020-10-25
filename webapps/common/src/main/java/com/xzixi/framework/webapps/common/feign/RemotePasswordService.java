package com.xzixi.framework.webapps.common.feign;

import com.xzixi.framework.boot.webmvc.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xuelingkang
 * @date 2020-10-25
 */
@FeignClient(value = "portal-sso", path = "/password")
public interface RemotePasswordService {

    @GetMapping("/encode")
    Result<String> encode(String rawPassword);

    @GetMapping("/matches")
    Result<Boolean> matches(String rawPassword, String encodedPassword);
}
