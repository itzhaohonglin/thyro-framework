package com.thyro.framework.common.model;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 分页参数
 */
@Data
public class PageDTO {

    /**
     * 页码
     */
    @NotNull(message = "分页页码参数为必填项")
    @Min(value = 1, message = "分页页码不能小于1")
    private Integer pageIndex;

    /**
     * 分页大小
     */
    @NotNull(message = "分页大小参数为必填项")
    @Min(value = 1, message = "分页大小不能小于1")
    private Integer length;

    /**
     * 排序（在字段名后加“:asc或:desc”指定升序（降序），多个字段使用逗号分隔，省略排序默认使用升序）
     * example = "“字段1,字段2” 或者 “字段1:asc,字段2:desc”
     */
    private String order;

    /**
     * 转成Page对象
     * 排序sort传参格式：“字段1,字段2”（升序） 或者 “字段1:asc,字段2:desc”（指定顺序），asc和desc大小写严格区分
     */
    public <T> Page<T> page() {
        Page<T> page = new Page<>(this.pageIndex, this.length);
        if (StringUtils.isNotEmpty(order)) {
            String[] orderList = order.split(",");
            List<OrderItem> orderItemList = Stream.of(orderList).map(o -> {
                String[] arr = o.split(":");
                boolean isAsc = arr.length == 1 || "asc".equals(arr[1]);
                return new OrderItem().setColumn(arr[0]).setAsc(isAsc);
            }).collect(Collectors.toList());
            page.setOrders(orderItemList);
        }
        return page;
    }

}
