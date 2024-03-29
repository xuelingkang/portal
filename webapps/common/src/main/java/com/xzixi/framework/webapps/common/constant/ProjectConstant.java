/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.common.constant;

import org.springframework.http.MediaType;

/**
 * @author 薛凌康
 */
public interface ProjectConstant {

    String RESPONSE_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;

    String INDEX_NAME_PREFIX = "portal-";

    String APP_UID_NAME = "appUid";

    String SIGN_NAME = "sign";

    String TIMESTAMP_NAME = "timestamp";
}
