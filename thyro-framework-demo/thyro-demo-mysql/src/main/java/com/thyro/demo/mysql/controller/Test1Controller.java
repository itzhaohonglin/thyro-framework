package com.thyro.demo.mysql.controller;

import com.thyro.demo.mysql.entity.Test1;
import com.thyro.demo.mysql.service.impl.Test1ServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 * 测试1表 前端控制器
 * </p>
 *
 * @since 2025-01-18
 */
@Controller
@RequestMapping("/mysql/test1")
public class Test1Controller {

    @Resource
    private Test1ServiceImpl test1Service;

    @GetMapping("/{id}")
    public Test1 test1(@PathVariable Integer id) {
        return test1Service.getById(id);
    }

    @GetMapping("/test")
    public Test1 test1Demo(Integer id) {
        return test1Service.getById(id);
    }
}
