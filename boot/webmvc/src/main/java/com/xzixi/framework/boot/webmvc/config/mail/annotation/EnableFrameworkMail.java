package com.xzixi.framework.boot.webmvc.config.mail.annotation;

import com.xzixi.framework.boot.webmvc.config.mail.MailConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MailConfig.class)
public @interface EnableFrameworkMail {
}
