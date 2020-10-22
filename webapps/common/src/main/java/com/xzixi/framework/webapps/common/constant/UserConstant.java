package com.xzixi.framework.webapps.common.constant;

/**
 * @author 薛凌康
 */
public interface UserConstant {

    Integer SYSTEM_ADMIN_USER_ID = 1;

    long USER_ACTIVATE_EXPIRE_DAY = 7;
    long USER_ACTIVATE_EXPIRE_SECOND = USER_ACTIVATE_EXPIRE_DAY * 24 * 60 * 60L;
    String USER_ACTIVATE_KEY_TEMPLATE = "userActivate::%s";
    String USER_ACTIVATE_MESSAGE_TEMPLATE = "激活链接已经发送至%s，请在%s天内完成激活操作，谢谢！";
    String USER_ACTIVATE_MAIL_TITLE = "激活账户";
    String USER_ACTIVATE_MAIL_CONTENT_TEMPLATE = "<p>尊敬的%s您好：</p>" +
            "<p>感谢您的注册，</p>" +
            "<p>请点击<a href='%s'>链接</a>激活账户。</p>" +
            "<p>或复制以下链接，粘贴到浏览器地址栏直接访问：</p>" +
            "<p>%s</p>" +
            "<p>链接有效期%s天，请尽快激活，过期后请重新注册。</p>";

    long REBIND_EMAIL_CODE_RETRY_TIMEOUT_SECOND = 60L;
    long REBIND_EMAIL_CODE_RETRY_TIMEOUT_MILLISECOND = REBIND_EMAIL_CODE_RETRY_TIMEOUT_SECOND * 1000L;
    String REBIND_EMAIL_CODE_RETRY_TIMEOUT_KEY_TEMPLATE = "rebindEmailCodeRetryTimeout::%s";
    String REBIND_EMAIL_CODE_KEY_TEMPLATE = "rebindEmailCode::%s:%s";
    int REBIND_EMAIL_CODE_LENGTH = 6;
    long REBIND_EMAIL_CODE_KEY_EXPIRE_MINUTE = 30L;
    long REBIND_EMAIL_CODE_KEY_EXPIRE_SECOND = REBIND_EMAIL_CODE_KEY_EXPIRE_MINUTE * 60L;
    String REBIND_EMAIL_MESSAGE_TEMPLATE = "更换邮箱验证码已经发送至%s，请在%s分钟内完成验证，谢谢！";
    String REBIND_EMAIL_CODE_MAIL_TITLE = "更换邮箱验证码";
    String REBIND_EMAIL_CODE_MAIL_CONTENT_TEMPLATE = "<p>尊敬的%s您好：</p>" +
            "<p>您的更换邮箱验证码是：%s。</p>" +
            "<p>验证码有效期%s分钟，请尽快完成绑定操作。</p>" +
            "<p>如果您没有进行该操作请无视此邮件。</p>";
}
