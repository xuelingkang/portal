/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.boot.webmvc.model.search;

import com.xzixi.framework.boot.webmvc.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collections;
import java.util.List;

/**
 * 分页参数
 *
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
public class Pagination<T extends BaseModel> {

    /**
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    private long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

    /**
     * 排序字段信息
     */
    private String[] orders;

    /**
     * 自动优化 COUNT SQL
     */
    private boolean optimizeCountSql = true;

    /**
     * 是否进行 count 查询
     */
    private boolean isSearchCount = true;

    public Pagination(long current, long size) {
        this(current, size, 0);
    }

    public Pagination(long current, long size, long total) {
        this(current, size, total, true);
    }

    public Pagination(long current, long size, boolean isSearchCount) {
        this(current, size, 0, isSearchCount);
    }

    public Pagination(long current, long size, long total, boolean isSearchCount) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    public Pagination<T> orders(String... orders) {
        this.orders = ArrayUtils.addAll(this.orders, orders);
        return this;
    }

    @SuppressWarnings("unused")
    public long getPages() {
        if (size == 0) {
            return 0L;
        }
        long pages = total / size;
        if (total % size != 0) {
            pages++;
        }
        return pages;
    }
}
