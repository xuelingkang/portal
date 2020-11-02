/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
