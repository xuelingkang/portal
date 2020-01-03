package com.xzixi.self.portal.webapp.model.vo;

import com.xzixi.self.portal.framework.util.BeanUtils;
import com.xzixi.self.portal.webapp.model.po.Attachment;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "附件")
public class AttachmentVO extends Attachment {

    private static final long serialVersionUID = 1L;

    public AttachmentVO(Attachment attachment, String... ignoreProperties) {
        BeanUtils.copyProperties(attachment, this, ignoreProperties);
    }

    @Getter
    @AllArgsConstructor
    public static class BuildOption {
    }
}
