package com.xzixi.self.portal.framework.sftp.client.util;

import com.xzixi.self.portal.framework.sftp.client.exception.SftpClientException;

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
