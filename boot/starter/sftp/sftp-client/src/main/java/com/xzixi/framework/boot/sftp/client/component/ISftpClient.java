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

package com.xzixi.framework.boot.sftp.client.component;

/**
 * @author 薛凌康
 */
public interface ISftpClient {

    /**
     * 获取sftp连接，执行handler的操作
     *
     * @param handler 具体的sftp操作
     */
    void open(Handler handler);

    interface Handler {

        /**
         * 执行sftp操作
         *
         * @param sftp Sftp实例
         * @see Sftp
         * @throws Exception sftp操作可能抛出的任何异常
         */
        void doHandle(Sftp sftp) throws Exception;
    }
}
