package com.xzixi.framework.backend.service;

import com.xzixi.framework.common.model.po.Token;

/**
 * @author 薛凌康
 */
public interface ITokenService {

    /**
     * 生成并保存token
     *
     * @param userId 用户id
     * @return Token
     */
    Token saveToken(Integer userId);

    /**
     * 获取token对象
     *
     * @param signature token签名
     * @return Token
     */
    Token getToken(String signature);

    /**
     * 更新token过期时间
     *
     * @param signature token签名
     * @return Token
     */
    Token refreshToken(String signature);

    /**
     * 删除token签名对应的token对象
     *
     * @param signature token签名
     */
    void deleteToken(String signature);
}
