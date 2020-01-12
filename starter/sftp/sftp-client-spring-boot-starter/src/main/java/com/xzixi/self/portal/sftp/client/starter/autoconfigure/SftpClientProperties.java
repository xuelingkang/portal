package com.xzixi.self.portal.sftp.client.starter.autoconfigure;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * sftp连接池配置参数
 *
 * @author 薛凌康
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "sftp-client")
public class SftpClientProperties extends CommonProperties {

    /** 默认为单个 */
    private boolean multiple = false;

    /**
     * 当type=ClientType.MULTIPLE时设置
     */
    private Map<String, CommonProperties> clients = new LinkedHashMap<>();
}
