package com.xzixi.self.portal.sftp.pool.component;

import com.xzixi.self.portal.sftp.pool.exception.SftpClientException;
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
            Handler policyHandler = new DelegateHandler(handler);
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
    static class DelegateHandler implements Handler {

        private Handler target;

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

    public interface Handler {

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
