package com.xzixi.self.portal.webapp.model.po;

import com.xzixi.self.portal.webapp.framework.model.BaseModel;
import com.xzixi.self.portal.webapp.model.enums.AuthorityCategory;
import com.xzixi.self.portal.webapp.model.enums.AuthorityMethod;
import com.xzixi.self.portal.webapp.model.enums.AuthorityProtocol;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "权限")
public class Authority extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限id")
    private Integer id;
    @ApiModelProperty(value = "协议类型", allowableValues = "HTTP,WEBSOCKET")
    private AuthorityProtocol protocol;
    @ApiModelProperty(value = "权限类别", allowableValues = "USER,ROLE,AUTHORITY,ATTACHMENT,JOB,MAIL,ARTICLE,LETTER")
    private AuthorityCategory category;
    @ApiModelProperty(value = "权限顺序")
    private Integer seq;
    @ApiModelProperty(value = "权限的ant path")
    private String pattern;
    @ApiModelProperty(value = "请求方法", allowableValues = "GET,HEAD,DELETE,POST,PUT,PATCH,SUBSCRIBE")
    private AuthorityMethod method;
    @ApiModelProperty(value = "权限描述")
    private String description;
}
