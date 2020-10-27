package com.xzixi.framework.boot.webmvc.service;

import java.util.Map;

/**
 * 签名工具
 *
 * @author xuelingkang
 * @date 2020-10-27
 */
public interface ISignService {

    /**
     * 默认超时时间 5分钟
     */
    long DEFAULT_TIMEOUT = 5 * 60 * 1000;

    /**
     * 密钥参数名称
     */
    String SECRET_NAME = "secret";

    /**
     * 时间戳参数名称
     */
    String TIMESTAMP_NAME = "timestamp";

    /**
     * 键值对格式
     */
    String PAIR_FORMAT = "%s=%s";

    /**
     * 键值对分隔符
     */
    String DELIMITER = "&";

    /**
     * 使用密钥对参数签名
     *
     * @param params 参数，必须包含时间戳
     * @param secret 密钥
     * @return 签名
     */
    String genSign(Map<String, Object> params, String secret);

    /**
     * 验证签名
     *
     * @param params 参数，必须包含时间戳
     * @param sign 签名
     * @param secret 密钥
     * @param timeout 超时时间
     * @return boolean
     */
    boolean check(Map<String, Object> params, String sign, String secret, long timeout);

    /**
     * 使用默认超时时间验证签名
     *
     * @param params 参数，必须包含时间戳
     * @param sign 签名
     * @param secret 密钥
     * @return boolean
     */
    default boolean check(Map<String, Object> params, String sign, String secret) {
        return check(params, sign, secret, DEFAULT_TIMEOUT);
    }
}
