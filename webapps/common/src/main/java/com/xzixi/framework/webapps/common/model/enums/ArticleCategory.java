package com.xzixi.framework.webapps.common.model.enums;

import com.xzixi.framework.boot.webmvc.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章栏目
 *
 * @author 薛凌康
 */
@Getter
@AllArgsConstructor
public enum ArticleCategory implements IBaseEnum {

    /** 博客 */
    BLOG("博客"),
    /** 新闻 */
    NEWS("新闻"),
    /** 其他 */
    OTHER("其他");

    private final String value;
}
