package com.xzixi.self.portal.webapp.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author 薛凌康
 */
public class PageUtil {

    /**
     * 转换IPage中记录的类型
     *
     * @param page 原page对象
     * @param records 新page对象中的记录
     * @param <T> 原page对象中的记录类型
     * @param <R> 新page对象中的记录类型
     * @return Page&lt;R>
     */
    public static <T, R> Page<R> convert(IPage<T> page, List<R> records) {
        Page<R> rPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal(), page.isSearchCount());
        rPage.setRecords(records);
        return rPage;
    }
}
