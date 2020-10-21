package com.xzixi.framework.common.model.vo;

import com.xzixi.framework.boot.webmvc.util.BeanUtils;
import com.xzixi.framework.common.model.po.Attachment;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
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
