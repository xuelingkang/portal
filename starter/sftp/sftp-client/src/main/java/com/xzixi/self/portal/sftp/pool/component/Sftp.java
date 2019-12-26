package com.xzixi.self.portal.sftp.pool.component;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.xzixi.self.portal.sftp.pool.exception.SftpClientException;
import com.xzixi.self.portal.sftp.pool.util.ByteUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sftp {

    private ChannelSftp channelSftp;

    /**
     * 下载远程文件
     *
     * @param dir 文件目录
     * @param name 文件名
     * @return 文件字节数组
     */
    public byte[] download(String dir, String name) {
        try {
            channelSftp.cd(dir);
            InputStream in = channelSftp.get(name);
            return ByteUtil.inputStreamToByteArray(in);
        } catch (SftpException e) {
            throw new SftpClientException("sftp下载文件出错", e);
        }
    }

    /**
     * 上传文件
     *
     * @param dir 远程目录
     * @param name 远程文件名
     * @param in 输入流
     */
    public void upload(String dir, String name, InputStream in) {
        try {
            mkdirs(dir);
            channelSftp.cd(dir);
            channelSftp.put(in, name);
        } catch (SftpException e) {
            throw new SftpClientException("sftp上传文件出错", e);
        }
    }

    /**
     * 删除文件
     *
     * @param dir 远程目录
     * @param name 远程文件名
     */
    public void delete(String dir, String name) {
        try {
            channelSftp.cd(dir);
            channelSftp.rm(name);
        } catch (SftpException e) {
            throw new SftpClientException("sftp删除文件出错", e);
        }
    }

    /**
     * 递归创建目录
     *
     * @param dir 目录绝对路径
     */
    public void mkdirs(String dir) {
        String[] folders = dir.split("/");
        try {
            channelSftp.cd("/");
            for (String folder: folders) {
                if (folder.length()>0) {
                    try {
                        channelSftp.cd(folder);
                    } catch (Exception e) {
                        channelSftp.mkdir(folder);
                        channelSftp.cd(folder);
                    }
                }
            }
        } catch (SftpException e) {
            throw new SftpClientException("sftp创建目录出错", e);
        }
    }
}
