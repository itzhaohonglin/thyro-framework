package com.thyro.demo.job;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class JobDemoApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(JobDemoApplication.class, args);
        } catch (Exception e) {
            log.error("系统异常.", e);
        }
    }
}
