package com.xzixi.self.portal.webapp.model.enums;

import com.xzixi.self.portal.framework.model.IBaseEnum;
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

    private String value;
}
