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
