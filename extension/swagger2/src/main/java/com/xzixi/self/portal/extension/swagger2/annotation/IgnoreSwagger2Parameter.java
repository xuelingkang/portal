package com.xzixi.self.portal.extension.swagger2.annotation;

import java.lang.annotation.*;

/**
 * 设置swagger2文档需要忽略的参数
 *
 * @author 薛凌康
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreSwagger2Parameter {
}
