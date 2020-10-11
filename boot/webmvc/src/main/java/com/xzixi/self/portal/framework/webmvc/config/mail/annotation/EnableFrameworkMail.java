package com.xzixi.self.portal.framework.webmvc.config.mail.annotation;

import com.xzixi.self.portal.framework.webmvc.config.mail.MailConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MailConfig.class)
public @interface EnableFrameworkMail {
}
