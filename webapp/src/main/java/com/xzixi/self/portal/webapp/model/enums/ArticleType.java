package com.xzixi.self.portal.webapp.model.enums;

import com.xzixi.self.portal.framework.webmvc.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章类型
 *
 * @author 薛凌康
 */
@Getter
@AllArgsConstructor
public enum ArticleType implements IBaseEnum {

    /** 原创 */
    ORIGINAL("原创"),
    /** 转载 */
    REPRINT("转载"),
    /** 翻译 */
    TRANSLATE("翻译");

    private final String value;
}
