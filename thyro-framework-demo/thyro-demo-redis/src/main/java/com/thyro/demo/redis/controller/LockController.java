package com.thyro.demo.redis.controller;


import com.thyro.demo.redis.Product;
import com.thyro.demo.redis.service.LockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 锁 控制器
 */
@RestController
@RequestMapping("lock")
@Slf4j
public class LockController {
    @Resource
    private LockService lockService;

    /**
     * 简单示例
     *
     * @param key key
     */
    @GetMapping("/simple")
    public int lock(String key) throws InterruptedException {
        return lockService.lock(key);
    }

    /**
     * 锁两个参数
     *
     * @param key  key
     * @param key2 key2
     */

    @GetMapping("/lock2params")
    public int lock2(String key, String key2) throws InterruptedException {
        return lockService.lock2(key, key2);
    }

    /**
     * 锁入参对象中的属性
     * 此处示例锁商品id
     */
    @PostMapping("/lockObject")
    public int lockObject(Product product) throws InterruptedException {
        return lockService.lockObject(product);
    }

}
