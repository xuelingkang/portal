package com.xzixi.framework.webapp.model.enums;

import com.xzixi.framework.boot.webmvc.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 薛凌康
 */
@Getter
@AllArgsConstructor
public enum AttachmentType implements IBaseEnum {

    /** 邮件附件 */
    MAIL("邮件附件"),
    /** 博客附件*/
    BLOG("博客附件"),
    /** 新闻附件*/
    NEWS("新闻附件");

    private final String value;
}
