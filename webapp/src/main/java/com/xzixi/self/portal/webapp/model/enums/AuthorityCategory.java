package com.xzixi.self.portal.webapp.model.enums;

import com.xzixi.self.portal.webapp.framework.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权限类别
 *
 * @author 薛凌康
 */
@Getter
@AllArgsConstructor
public enum AuthorityCategory implements IBaseEnum {

    /** 枚举 */
    ENUM("枚举"),
    /** 认证 */
    AUTHORIZATION("认证"),
    /** 用户 */
    USER("用户"),
    /** 角色 */
    ROLE("角色"),
    /** 权限 */
    AUTHORITY("权限"),
    /** 附件 */
    ATTACHMENT("附件"),
    /** 任务 */
    JOB("任务"),
    /** 邮件 */
    MAIL("邮件"),
    /** 博客 */
    BLOG("博客"),
    /** 新闻 */
    NEWS("新闻"),
    /** 留言 */
    LETTER("留言");

    private String value;
}
