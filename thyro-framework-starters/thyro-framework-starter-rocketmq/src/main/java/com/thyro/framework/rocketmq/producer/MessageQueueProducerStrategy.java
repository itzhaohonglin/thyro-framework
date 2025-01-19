package com.thyro.framework.rocketmq.producer;


import com.thyro.framework.common.mq.MqConfig;

public interface MessageQueueProducerStrategy {

    /**
     * mq生产者
     *
     * @param topic    消息主题
     * @param mqConfig 消息参数配置
     * @param body     消息内容
     */
    void producer(String topic, MqConfig mqConfig, Object body);
}
