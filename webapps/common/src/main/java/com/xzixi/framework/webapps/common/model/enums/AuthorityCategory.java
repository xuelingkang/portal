package com.xzixi.framework.webapps.common.model.enums;

import com.xzixi.framework.boot.webmvc.model.IBaseEnum;
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

    /** 系统 */
    SYSTEM("系统"),
    /** 枚举 */
    ENUM("枚举"),
    /** 认证 */
    AUTHORIZATION("认证"),
    /** 用户 */
    USER("用户"),
    /** 关注 */
    USER_LINK("关注"),
    /** 角色 */
    ROLE("角色"),
    /** 权限 */
    AUTHORITY("权限"),
    /** 附件 */
    ATTACHMENT("附件"),
    /** 任务模板 */
    JOB_TEMPLATE("任务模板"),
    /** 定时任务 */
    JOB("定时任务"),
    /** 邮件 */
    MAIL("邮件"),
    /** 文章 */
    ARTICLE("文章"),
    /** 回复 */
    REPLY("回复");

    private final String value;
}
