package com.thyro.demo.redis;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品
 */
@Data
public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
}
