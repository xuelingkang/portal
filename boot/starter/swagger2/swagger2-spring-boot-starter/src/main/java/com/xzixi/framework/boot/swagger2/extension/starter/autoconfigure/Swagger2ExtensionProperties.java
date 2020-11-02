/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.boot.swagger2.extension.starter.autoconfigure;

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
        private boolean enable;
        private String excludeUrlReg;
        private String templatePath = "/default-authentication.json";
        private String paramName = "Authorization";
        private String headerName = "x-access-token";
    }
}
