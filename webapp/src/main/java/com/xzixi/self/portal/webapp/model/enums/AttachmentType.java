package com.xzixi.self.portal.webapp.model.enums;

import com.xzixi.self.portal.webapp.framework.model.IBaseEnum;
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

    private String value;
}