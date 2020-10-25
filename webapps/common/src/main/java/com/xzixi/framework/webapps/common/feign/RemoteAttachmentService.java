package com.xzixi.framework.webapps.common.feign;

import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.boot.webmvc.model.search.Pagination;
import com.xzixi.framework.webapps.common.model.enums.AttachmentType;
import com.xzixi.framework.webapps.common.model.params.AttachmentSearchParams;
import com.xzixi.framework.webapps.common.model.vo.AttachmentVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author xuelingkang
 * @date 2020-10-25
 */
@FeignClient(value = "portal-file", path = "/attachment")
public interface RemoteAttachmentService {

    @PostMapping("/{type}")
    Result<AttachmentVO> upload(MultipartFile file, @PathVariable AttachmentType type);

    @GetMapping
    Result<Pagination<AttachmentVO>> page(AttachmentSearchParams searchParams);

    @GetMapping("/{id}")
    Result<AttachmentVO> getById(@PathVariable Integer id);

    @DeleteMapping
    Result<?> remove(List<Integer> ids);
}
