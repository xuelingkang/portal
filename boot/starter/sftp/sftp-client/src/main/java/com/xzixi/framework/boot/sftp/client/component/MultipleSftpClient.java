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

package com.xzixi.framework.boot.sftp.client.component;

import com.xzixi.framework.boot.sftp.client.exception.SftpClientException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 多个sftpClient
 *
 * @author 薛凌康
 */
public class MultipleSftpClient implements ISftpClient {

    private Map<String, ISftpClient> clientMap = new LinkedHashMap<>();
    private ThreadLocal<ISftpClient> threadLocal = new ThreadLocal<>();

    @Override
    public void open(Handler handler) {
        ISftpClient client = threadLocal.get();
        if (client == null) {
            throw new SftpClientException("请先选择sftpClient！");
        }
        client.open(handler);
    }

    /**
     * 选择sftpClient
     *
     * @param name sftpClient的名称
     */
    public void choose(String name) {
        threadLocal.remove();
        threadLocal.set(clientMap.get(name));
    }

    public void put(String name, ISftpClient client) {
        clientMap.put(name, client);
    }
}
