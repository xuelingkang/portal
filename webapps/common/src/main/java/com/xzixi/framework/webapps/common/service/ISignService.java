package com.xzixi.framework.webapps.common.service;

import java.util.Map;

import static com.xzixi.framework.webapps.common.constant.SignConstant.DEFAULT_TIMEOUT;

/**
 * 签名工具
 *
 * @author xuelingkang
 * @date 2020-10-27
 */
public interface ISignService {

    /**
     * 生成随机密钥
     *
     * @param length 密钥长度
     * @return 密钥
     */
    String genSecret(int length);

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
