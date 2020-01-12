package com.xzixi.self.portal.sftp.client.component;

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
