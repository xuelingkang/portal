package com.xzixi.framework.common.model.params;

import com.xzixi.framework.boot.webmvc.model.search.BaseSearchParams;
import com.xzixi.framework.common.model.po.Attachment;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "附件查询参数")
public class AttachmentSearchParams extends BaseSearchParams<Attachment> {
}