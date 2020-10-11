package com.xzixi.framework.boot.webmvc.config.mail;

import org.springframework.context.annotation.Configuration;

/**
 * @author 薛凌康
 */
@Configuration
public class MailConfig {

    static {
        // 防止中文名字附件base64加密以后，名字太长被截断，导致中文乱码问题
        System.setProperty("mail.mime.splitlongparameters", "false");
    }
}
