package com.xzixi.self.portal.framework.swagger2.extension.annotation;

import com.xzixi.self.portal.framework.swagger2.extension.component.ModelAttributeParameterExpanderExtension;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.*;

/**
 * 开启swagger2扩展，同时会开启原生swagger2
 *
 * @author 薛凌康
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableSwagger2
@Import({ModelAttributeParameterExpanderExtension.class})
public @interface EnableSwagger2Extension {
}
