package com.xzixi.self.portal.sftp.pool.component;

import com.xzixi.self.portal.sftp.pool.exception.SftpPoolException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SftpClient {

    private SftpPool sftpPool;

    /**
     * 从sftp连接池获取连接并执行操作
     *
     * @param handler sftp操作
     */
    public void open(Handler handler) {
        Sftp sftp = null;
        try {
            sftp = sftpPool.borrowObject();
            handler.doHandle(sftp);
        } catch (Exception e) {
            throw new SftpPoolException("获取sftp连接出错！", e);
        } finally {
            if (sftp != null) {
                sftpPool.returnObject(sftp);
            }
        }
    }

    public interface Handler {

        /**
         * 执行sftp操作
         *
         * @param sftp Sftp实例
         * @see Sftp
         */
        void doHandle(Sftp sftp);
    }
}
