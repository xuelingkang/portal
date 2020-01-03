package com.xzixi.self.portal.webapp.config;

import com.xzixi.self.portal.extension.swagger2.annotation.EnableSwagger2Extension;
import com.xzixi.self.portal.webapp.config.swagger2.Swagger2Filter;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.web.Swagger2Controller;

import java.util.Collections;
import java.util.List;

import static com.xzixi.self.portal.webapp.constant.SecurityConstant.AUTHENTICATION_HEADER_NAME;
import static com.xzixi.self.portal.webapp.constant.Swagger2Constant.*;

/**
 * swagger2配置
 *
 * @author 薛凌康
 */
@Configuration
@ConditionalOnExpression("${swagger2.enable}")
@EnableSwagger2Extension
public class Swagger2Config {

    @Value("${project.name}")
    private String projectName;
    @Value("${project.version}")
    private String projectVersion;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // 只显示添加@Api注解的类
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Swagger2Filter swagger2Filter() {
        return new Swagger2Filter();
    }

    @Bean
    public FilterRegistrationBean<Swagger2Filter> swaggerFilterFilterRegistrationBean(Swagger2Filter swagger2Filter) {
        FilterRegistrationBean<Swagger2Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(swagger2Filter);
        registration.addUrlPatterns(Swagger2Controller.DEFAULT_URL);
        registration.setName(AUTHORIZATION_API_FILTER_NAME);
        return registration;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(projectName)
                .version(projectVersion)
                .build();
    }

    private List<ApiKey> securitySchemes() {
        return Collections.singletonList(new ApiKey(AUTHORIZATION_PARAMETER_NAME, AUTHENTICATION_HEADER_NAME, AUTHORIZATION_PARAMETER_TYPE));
    }

    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(securityReferences())
                .forPaths(PathSelectors.regex(AUTHORIZATION_EXCLUDE_URL_REG))
                .build());
    }

    private List<SecurityReference> securityReferences() {
        AuthorizationScope[] authorizationScopes
                = new AuthorizationScope[]{new AuthorizationScope(AUTHORIZATION_SCOPE_NAME, AUTHORIZATION_SCOPE_DESCRIPTION)};
        return Collections.singletonList(new SecurityReference(AUTHORIZATION_PARAMETER_NAME, authorizationScopes));
    }
}
