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

package com.xzixi.framework.webapps.system.data.impl;

import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapps.system.data.IAppData;
import com.xzixi.framework.webapps.system.mapper.AppMapper;
import com.xzixi.framework.webapps.common.model.po.App;
import org.springframework.stereotype.Service;

/**
 * 应用 数据实现类
 *
 * @author xuelingkang
 * @date 2020-10-25
 */
@Service
public class AppDataImpl extends MybatisPlusDataImpl<AppMapper, App> implements IAppData {

}
