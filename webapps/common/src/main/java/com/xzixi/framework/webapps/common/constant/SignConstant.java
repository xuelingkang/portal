package com.xzixi.framework.webapps.common.constant;

/**
 * @author xuelingkang
 * @date 2020-10-27
 */
public interface SignConstant {

    /**
     * 默认超时时间 5分钟
     */
    long DEFAULT_TIMEOUT = 1000 * 60 * 5;

    /**
     * 密钥参数名称
     */
    String SECRET_NAME = "secret";

    /**
     * 签名参数名称
     */
    String SIGN_NAME = "sign";

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
}
