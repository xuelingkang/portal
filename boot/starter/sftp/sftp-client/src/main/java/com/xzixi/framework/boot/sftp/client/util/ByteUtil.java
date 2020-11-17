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

package com.xzixi.framework.boot.sftp.client.util;

import com.xzixi.framework.boot.sftp.client.exception.SftpClientException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author 薛凌康
 */
public class ByteUtil {

    /**
     * 输入流转字节数组
     * @param in 输入流
     * @return 字节数组
     */
    public static byte[] inputStreamToByteArray(InputStream in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024*4];
            int n;
            while ((n = in.read(buffer))>0) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        } catch (Exception e) {
            throw new SftpClientException("输入流转字节数组出错", e);
        }
    }
}
