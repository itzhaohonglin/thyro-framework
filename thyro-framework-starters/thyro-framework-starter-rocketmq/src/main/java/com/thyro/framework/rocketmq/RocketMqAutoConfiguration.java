package com.thyro.framework.rocketmq;


import com.thyro.framework.rocketmq.annotation.MessageQueueScan;
import com.thyro.framework.rocketmq.config.AliMqConfiguration;
import com.thyro.framework.rocketmq.config.OpenSourceMqConfiguration;
import com.thyro.framework.rocketmq.config.RocketMqConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@MessageQueueScan("${thyro.info.mq-scan:com.thyro.**.mq.**")
@EnableConfigurationProperties(RocketMqConfigurationProperties.class)

@Import({AliMqConfiguration.class, OpenSourceMqConfiguration.class})
public class RocketMqAutoConfiguration {

}
