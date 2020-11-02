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
