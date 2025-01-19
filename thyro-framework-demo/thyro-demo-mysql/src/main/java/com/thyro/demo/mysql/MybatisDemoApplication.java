package com.thyro.demo.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MybatisDemoApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(MybatisDemoApplication.class, args);
        } catch (Exception e) {
            log.error("启动异常...",e);
        }
    }
}
