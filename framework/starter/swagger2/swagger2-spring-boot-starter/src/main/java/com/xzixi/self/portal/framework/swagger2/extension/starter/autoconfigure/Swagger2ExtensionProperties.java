package com.xzixi.self.portal.framework.swagger2.extension.starter.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "swagger2")
public class Swagger2ExtensionProperties {

    private boolean enable;
    private String projectName;
    private String projectVersion;
    private AuthApi authApi = new AuthApi();

    @Data
    public static class AuthApi {
        private String excludeUrlReg;
        private String templatePath = "/default-authentication.json";
        private String paramName = "Authorization";
        private String headerName = "x-access-token";
    }
}
