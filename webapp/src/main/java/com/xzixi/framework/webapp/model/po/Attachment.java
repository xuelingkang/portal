package com.xzixi.framework.webapp.model.po;

import com.xzixi.framework.webapp.model.enums.AttachmentType;
import com.xzixi.framework.boot.webmvc.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "附件")
public class Attachment extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "附件id")
    private Integer id;

    @ApiModelProperty(value = "附件类型")
    private AttachmentType type;

    @ApiModelProperty(value = "附件名称")
    private String name;

    @ApiModelProperty(value = "访问路径")
    private String url;

    @ApiModelProperty(value = "磁盘路径")
    private String address;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;
}
