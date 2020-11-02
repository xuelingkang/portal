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

import com.xzixi.framework.boot.sftp.client.exception.SftpClientException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SftpClient implements ISftpClient {

    private SftpPool sftpPool;

    /**
     * 从sftp连接池获取连接并执行操作
     *
     * @param handler sftp操作
     */
    @Override
    public void open(ISftpClient.Handler handler) {
        Sftp sftp = null;
        try {
            sftp = sftpPool.borrowObject();
            ISftpClient.Handler policyHandler = new DelegateHandler(handler);
            policyHandler.doHandle(sftp);
        } catch (SftpClientException e) {
            throw e;
        } catch (Exception e) {
            throw new SftpClientException("获取sftp连接出错！", e);
        } finally {
            if (sftp != null) {
                sftpPool.returnObject(sftp);
            }
        }
    }

    @AllArgsConstructor
    static class DelegateHandler implements ISftpClient.Handler {

        private ISftpClient.Handler target;

        @Override
        public void doHandle(Sftp sftp) {
            try {
                target.doHandle(sftp);
            } catch (Exception e) {
                // 捕获sftp操作的所有异常，包装成SftpClientException
                throw new SftpClientException("执行sftp操作出错！", e);
            }
        }
    }
}
