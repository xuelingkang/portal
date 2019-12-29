package com.xzixi.self.portal.webapp.model.params;

import com.xzixi.self.portal.webapp.framework.model.BaseSearchParams;
import com.xzixi.self.portal.webapp.model.po.Attachment;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "附件查询参数")
public class AttachmentSearchParams extends BaseSearchParams<Attachment> {
}