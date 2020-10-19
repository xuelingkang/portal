package com.xzixi.framework.backend.model.enums;

import com.xzixi.framework.boot.webmvc.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章访问限制
 *
 * @author 薛凌康
 */
@Getter
@AllArgsConstructor
public enum ArticleAccess implements IBaseEnum {

    /** 公开 */
    PUBLIC("公开"),
    /** 个人 */
    PRIVATE("个人");

    private final String value;
}
