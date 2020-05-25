package com.xzixi.self.portal.sftp.client.component;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.xzixi.self.portal.sftp.client.exception.SftpClientException;
import com.xzixi.self.portal.sftp.client.util.ByteUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sftp {

    private ChannelSftp channelSftp;

    /**
     * 获取远程文件的输入流
     *
     * @param dir 文件目录
     * @param name 文件名
     * @return 远程文件流
     */
    public InputStream getInputStream(String dir, String name) {
        if (!isExist(dir)) {
            throw new SftpClientException(String.format("目录(%s)不存在！", dir));
        }
        String absoluteFilePath = dir + "/" + name;
        if (!isExist(absoluteFilePath)) {
            throw new SftpClientException(String.format("文件(%s)不存在！", absoluteFilePath));
        }
        try {
            channelSftp.cd(dir);
            return channelSftp.get(name);
        } catch (SftpException e) {
            throw new SftpClientException("sftp获取远程文件输入流错误", e);
        }
    }

    /**
     * 下载远程文件
     *
     * @param dir 文件目录
     * @param name 文件名
     * @return 文件字节数组
     */
    public byte[] download(String dir, String name) {
        InputStream in = getInputStream(dir, name);
        return ByteUtil.inputStreamToByteArray(in);
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
     * 上传文件
     *
     * @param dir 远程目录
     * @param name 远程文件名
     * @param src 本地文件路径
     */
    public void upload(String dir, String name, String src) {
        try {
            mkdirs(dir);
            channelSftp.cd(dir);
            channelSftp.put(src, name);
        } catch (SftpException e) {
            throw new SftpClientException("sftp上传文件出错", e);
        }
    }

    /**
     * 删除目录
     *
     * @param dir 远程目录
     */
    public void delete(String dir) {
        if (!isDir(dir)) {
            return;
        }
        if (!isExist(dir)) {
            return;
        }
        try {
            channelSftp.rmdir(dir);
        } catch (SftpException e) {
            throw new SftpClientException("sftp删除目录出错", e);
        }
    }

    /**
     * 删除文件
     *
     * @param dir 远程目录
     * @param name 远程文件名
     */
    public void delete(String dir, String name) {
        if (!isDir(dir)) {
            return;
        }
        String absoluteFilePath = dir + "/" + name;
        if (!isExist(absoluteFilePath)) {
            return;
        }
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

    /**
     * 判断文件或目录是否存在
     *
     * @param path 文件或目录路径
     * @return {@code true} 存在 {@code false} 不存在
     */
    public boolean isExist(String path) {
        try {
            channelSftp.lstat(path);
            return true;
        } catch (SftpException e) {
            return false;
        }
    }

    /**
     * 判断是否目录
     *
     * @param path 待判断的路径
     * @return {@code true} 是目录 {@code false} 不是目录
     */
    public boolean isDir(String path) {
        try {
            SftpATTRS attrs = channelSftp.lstat(path);
            return attrs.isDir();
        } catch (SftpException e) {
            return false;
        }
    }

    /**
     * 查看远程目录下的文件和目录
     *
     * @param path 远程目录路径
     * @return 目录下的文件和目录名称集合
     */
    public List<String> list(String path) {
        List<String> result = new ArrayList<>();
        ChannelSftp.LsEntrySelector selector = lsEntry -> {
            String filename = lsEntry.getFilename();
            if (!".".equals(filename) && !"..".equals(filename)) {
                result.add(filename);
            }
            return ChannelSftp.LsEntrySelector.CONTINUE;
        };
        try {
            channelSftp.ls(path, selector);
        } catch (SftpException e) {
            throw new SftpClientException("sftp查看目录出错", e);
        }
        return result;
    }

    /**
     * 移动或重命名文件
     *
     * @param src 源文件
     * @param target 目标文件
     */
    public void move(String src, String target) {
        try {
            channelSftp.rename(src, target);
        } catch (SftpException e) {
            throw new SftpClientException("sftp移动文件出错", e);
        }
    }

    /**
     * 修改权限
     *
     * @param permissions 权限，三位0-7的数字
     * @param path 绝对路径
     */
    public void chmod(String permissions, String path) {
        if (permissions == null) {
            throw new SftpClientException("权限不能为null");
        }
        if (permissions.length() != 3) {
            throw new SftpClientException("权限必须是3位0-7的数字");
        }
        for (char c : permissions.toCharArray()) {
            int i;
            try {
                i = Integer.parseInt(String.valueOf(c));
            } catch (NumberFormatException e) {
                throw new SftpClientException("权限必须是3位0-7的数字");
            }
            if (i > 7 || i < 0) {
                throw new SftpClientException("权限必须是3位0-7的数字");
            }
        }
        Integer p = Integer.valueOf(permissions, 8);
        try {
            channelSftp.chmod(p, path);
        } catch (SftpException e) {
            throw new SftpClientException("sftp修改权限出错", e);
        }
    }
}
