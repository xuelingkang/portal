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

package com.xzixi.framework.webapps.notice.service;

import com.xzixi.framework.boot.webmvc.service.IBaseService;
import com.xzixi.framework.webapps.common.model.po.MailContent;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
public interface IMailContentService extends IBaseService<MailContent> {

    /**
     * 根据邮件id查询
     *
     * @param mailIds 邮件id集合
     * @return List&lt;MailContent>
     */
    List<MailContent> listByMailIds(Collection<Integer> mailIds);
}
