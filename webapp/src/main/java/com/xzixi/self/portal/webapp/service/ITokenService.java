package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.webapp.model.po.Token;

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
     * @param tokenStr token字符串
     * @return Token
     */
    Token getToken(String tokenStr);

    /**
     * 更新token过期时间
     *
     * @param tokenStr token字符串
     * @return Token
     */
    Token refreshToken(String tokenStr);

    /**
     * 删除token字符串对应的token对象
     *
     * @param tokenStr token字符串
     */
    void deleteToken(String tokenStr);
}
