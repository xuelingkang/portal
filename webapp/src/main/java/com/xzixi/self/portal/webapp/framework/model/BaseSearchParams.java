package com.xzixi.self.portal.webapp.framework.model;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "查询参数")
public class BaseSearchParams<T> {

    private static final Pattern ASC_REG = Pattern.compile("\\s*[Aa][Ss][Cc]\\s*");

    @ApiModelProperty(value = "实体类参数")
    private T entity;

    @ApiModelProperty(value = "当前页")
    private Long current = 1L;

    @ApiModelProperty(value = "每页个数")
    private Long size = 10L;

    @ApiModelProperty(value = "排序规则")
    private String[] orderItems;

    @ApiModelProperty(value = "默认排序规则")
    private String[] defaultOrderItems;

    /**
     * 生成分页查询参数
     *
     * @return Page&lt;T>
     */
    public Page<T> buildPageParams() {
        Page<T> page = new Page<>(this.current, this.size);
        String[] orderItems = this.orderItems;
        if (ArrayUtils.isEmpty(orderItems)) {
            orderItems = this.defaultOrderItems;
        }
        List<OrderItem> orderItemList = Arrays.stream(orderItems)
            // 按空白拆分
            .map(item -> item.split("\\s+"))
            // 过滤出长度为2的数组
            .filter(arr -> arr.length == 2)
            .map(arr -> {
                String column = arr[0];
                boolean asc = ASC_REG.matcher(arr[1]).matches();
                if (asc) {
                    return OrderItem.asc(column);
                }
                return OrderItem.desc(column);
            }).collect(Collectors.toList());
        page.addOrder(orderItemList);
        return page;
    }

    /**
     * 生成查询参数
     *
     * @return QueryWrapper&lt;T>
     */
    public QueryWrapper<T> buildQueryWrapper() {
        return new QueryWrapper<>(this.entity);
    }
}
