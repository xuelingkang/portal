package com.xzixi.framework.boot.swagger2.extension.starter.autoconfigure;

import com.xzixi.framework.boot.swagger2.extension.annotation.EnableSwagger2Extension;
import com.xzixi.framework.boot.swagger2.extension.filter.Swagger2Filter;
import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Configuration
@ConditionalOnExpression("${swagger2.enable:false}")
@EnableSwagger2Extension
@EnableConfigurationProperties(Swagger2ExtensionProperties.class)
public class Swagger2ExtensionAutoConfiguration {

    private static final String AUTHENTICATION_PARAMETER_TYPE = "HEADER";
    private static final String AUTHENTICATION_SCOPE_NAME = "global";
    private static final String AUTHENTICATION_SCOPE_DESCRIPTION = "全局";
    private static final String AUTHENTICATION_API_FILTER_NAME = "swagger2Filter";

    @Resource
    Swagger2ExtensionProperties properties;

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.select().apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).build().apiInfo(apiInfo());
        if (properties.getAuthApi().isEnable()) {
            docket.securitySchemes(securitySchemes()).securityContexts(securityContexts());
        }
        return docket;
    }

    @Bean
    @ConditionalOnExpression("${swagger2.auth-api.enable:false}")
    public Swagger2Filter swagger2Filter() {
        return new Swagger2Filter(properties.getAuthApi().getTemplatePath());
    }

    @Bean
    @ConditionalOnExpression("${swagger2.auth-api.enable:false}")
    public FilterRegistrationBean<Swagger2Filter> swaggerFilterFilterRegistrationBean(Swagger2Filter swagger2Filter) {
        FilterRegistrationBean<Swagger2Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(swagger2Filter);
        registration.addUrlPatterns(Swagger2Controller.DEFAULT_URL);
        registration.setName(AUTHENTICATION_API_FILTER_NAME);
        return registration;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(properties.getProjectName())
                .version(properties.getProjectVersion())
                .build();
    }

    private List<ApiKey> securitySchemes() {
        Swagger2ExtensionProperties.AuthApi authApi = properties.getAuthApi();
        return Collections.singletonList(new ApiKey(authApi.getParamName(), authApi.getHeaderName(), AUTHENTICATION_PARAMETER_TYPE));
    }

    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(securityReferences())
                .forPaths(PathSelectors.regex(properties.getAuthApi().getExcludeUrlReg()))
                .build());
    }

    private List<SecurityReference> securityReferences() {
        AuthorizationScope[] authorizationScopes
                = new AuthorizationScope[]{new AuthorizationScope(AUTHENTICATION_SCOPE_NAME, AUTHENTICATION_SCOPE_DESCRIPTION)};
        return Collections.singletonList(new SecurityReference(properties.getAuthApi().getParamName(), authorizationScopes));
    }
}
