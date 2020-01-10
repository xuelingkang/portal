package com.xzixi.self.portal.sftp.client.component;

import com.jcraft.jsch.*;
import com.xzixi.self.portal.sftp.client.exception.SftpClientException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.Properties;

/**
 * sftp工厂
 *
 * @author 薛凌康
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SftpFactory extends BasePooledObjectFactory<Sftp> {

    private static final String CHANNEL_TYPE = "sftp";
    private static Properties sshConfig = new Properties();
    private String host;
    private int port;
    private String username;
    private String password;

    static {
        sshConfig.put("StrictHostKeyChecking", "no");
    }

    /**
     * 创建一个{@link Sftp}实例
     * 这个方法必须支持并发多线程调用
     *
     * @return {@link Sftp}实例
     */
    @Override
    public Sftp create() {
        try {
            JSch jsch = new JSch();
            Session sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            ChannelSftp channel = (ChannelSftp) sshSession.openChannel(CHANNEL_TYPE);
            channel.connect();
            return new Sftp(channel);
        } catch (JSchException e) {
            throw new SftpClientException("连接sftp失败", e);
        }
    }

    /**
     * 用{@link PooledObject}的实例包装对象
     *
     * @param sftp 被包装的对象
     * @return 对象包装器
     */
    @Override
    public PooledObject<Sftp> wrap(Sftp sftp) {
        return new DefaultPooledObject<>(sftp);
    }

    /**
     * 销毁对象
     *
     * @param p 对象包装器
     */
    @Override
    public void destroyObject(PooledObject<Sftp> p) {
        if (p!=null) {
            Sftp sftp = p.getObject();
            if (sftp!=null) {
                ChannelSftp channelSftp = sftp.getChannelSftp();
                if (channelSftp!=null) {
                    channelSftp.disconnect();
                }
            }
        }
    }

    /**
     * 检查连接是否可用
     *
     * @param p 对象包装器
     * @return {@code true} 可用，{@code false} 不可用
     */
    @Override
    public boolean validateObject(PooledObject<Sftp> p) {
        if (p!=null) {
            Sftp sftp = p.getObject();
            if (sftp!=null) {
                try {
                    sftp.getChannelSftp().pwd();
                    return true;
                } catch (SftpException e) {
                    return false;
                }
            }
        }
        return false;
    }

    public static class Builder {
        private String host;
        private int port;
        private String username;
        private String password;
        public SftpFactory build() {
            return new SftpFactory(host, port, username, password);
        }
        public Builder host(String host) {
            this.host = host;
            return this;
        }
        public Builder port(int port) {
            this.port = port;
            return this;
        }
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        public Builder password(String password) {
            this.password = password;
            return this;
        }
    }
}
