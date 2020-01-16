package com.xzixi.self.portal.framework.model.search;

import com.xzixi.self.portal.framework.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

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
