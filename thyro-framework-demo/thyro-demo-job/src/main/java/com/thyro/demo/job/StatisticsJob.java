package com.thyro.demo.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StatisticsJob {

    @XxlJob("executeStatisticsJob")
    public void executeStatisticsJob() {
        System.out.println("当前时间:" + System.currentTimeMillis());
    }
}
