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

package com.xzixi.framework.webapps.common.constant;

/**
 * @author 薛凌康
 */
public interface SecurityConstant {

    String NULL_TOKEN = "null";
    String AUTHENTICATION_HEADER_NAME = "x-access-token";
    String AUTHENTICATION_PARAMETER_NAME = "_access";
    long AUTHENTICATION_EXPIRE_SECOND = 7 * 24 * 60 * 60L;
    String AUTHENTICATION_JWT_SECRET = "S!E@L#F$P%O^R&T*A(L";
    String AUTHENTICATION_REDIS_KEY_TEMPLATE = "tokens::%s";
    String RESET_PASSWORD_KEY_TEMPLATE = "resetPassword::%s";
    long RESET_PASSWORD_RETRY_TIMEOUT_SECOND = 60L;
    long RESET_PASSWORD_RETRY_TIMEOUT_MILLISECOND = RESET_PASSWORD_RETRY_TIMEOUT_SECOND * 1000L;
    long RESET_PASSWORD_KEY_EXPIRE_MINUTE = 30L;
    long RESET_PASSWORD_KEY_EXPIRE_SECOND = RESET_PASSWORD_KEY_EXPIRE_MINUTE * 60L;
    String RESET_PASSWORD_MESSAGE_TEMPLATE = "重置密码链接已经发送至%s，请在%s分钟内完成重置操作，谢谢！";
    String RESET_PASSWORD_MAIL_TITLE = "重置密码";
    String RESET_PASSWORD_MAIL_CONTENT_TEMPLATE = "<p>尊敬的%s您好：</p>" +
            "<p>您正在进行找回密码的操作，</p>" +
            "<p>请点击<a href='%s'>链接</a>完成操作。</p>" +
            "<p>或复制以下链接，粘贴到浏览器地址栏直接访问：</p>" +
            "<p>%s</p>" +
            "<p>链接有效期%s分钟</p>" +
            "<p>如果您没有进行该操作请无视此邮件。</p>";
}
