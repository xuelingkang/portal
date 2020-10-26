package com.xzixi.framework.webapps.common.feign.annotation;

import com.xzixi.framework.webapps.common.feign.RemoteAppService;
import com.xzixi.framework.webapps.common.feign.RemoteAttachmentService;
import com.xzixi.framework.webapps.common.feign.RemoteUserService;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xuelingkang
 * @date 2020-10-25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RemoteAppService.class, RemoteAttachmentService.class, RemoteUserService.class})
public @interface EnableCommonRemoteServices {
}
