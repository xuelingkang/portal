package com.xzixi.self.portal.webapp.config;

import com.xzixi.swagger2.plus.annotation.EnableSwagger2Plus;
import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

import static com.xzixi.self.portal.webapp.framework.constant.SecurityConstant.AUTHENTICATION_HEADER_NAME;

/**
 * swagger2配置
 *
 * @author 薛凌康
 */
@Configuration
@ConditionalOnExpression("${swagger2.enable}==true")
@EnableSwagger2Plus
public class Swagger2PlusConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // 只显示添加@Api注解的类
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .globalOperationParameters(Collections.singletonList(tokenHeader()))
                .apiInfo(apiInfo());
    }

    private Parameter tokenHeader() {
        return new ParameterBuilder()
                .parameterType("header")
                .parameterAccess("access")
                .name(AUTHENTICATION_HEADER_NAME)
                .description("认证参数")
                .required(false)
                .modelRef(new ModelRef("string"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("portal")
                .version("1.0.0")
                .build();
    }
}
