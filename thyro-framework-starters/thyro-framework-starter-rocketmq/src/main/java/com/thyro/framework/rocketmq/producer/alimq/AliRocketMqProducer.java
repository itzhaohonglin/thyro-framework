package com.thyro.framework.rocketmq.producer.alimq;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.thyro.framework.common.mq.MqConfig;
import com.thyro.framework.rocketmq.exception.MqException;
import com.thyro.framework.rocketmq.producer.MessageQueueProducerStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;


@Slf4j
public class AliRocketMqProducer implements MessageQueueProducerStrategy {


    @Resource
    private Producer producer;

    @Resource
    private OrderProducer orderProducer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AliRocketMqProducer(JavaTimeModule javaTimeModule) {
        objectMapper.registerModule(javaTimeModule);
    }

    @Override
    public void producer(String topic, MqConfig mqConfig, Object body) {
        Message message = new Message();
        message.setTopic(topic);
        try {
            byte[] bodyBytes = objectMapper.writeValueAsBytes(body);
            message.setBody(bodyBytes);
        } catch (JsonProcessingException e) {
            throw new MqException(e);
        }

        if (mqConfig != null) {
            if (mqConfig.getStartDeliverTime() != null) {
                message.setStartDeliverTime(mqConfig.getStartDeliverTime());
            }
            if (mqConfig.getHashKey() != null) {
                message.setShardingKey(mqConfig.getHashKey());
            }
            if (mqConfig.getHashKey() != null) {
                message.setKey(mqConfig.getHashKey());
            }
            if (mqConfig.getTag() != null) {
                message.setTag(mqConfig.getTag());
            }
        }
        SendResult sendResult;
        if (StringUtils.isNotBlank(message.getShardingKey())) {
            sendResult = orderProducer.send(message, message.getShardingKey());
        } else {
            sendResult = producer.send(message);
        }
        log.info("mq send result topic [{}] msgId [{}] ", topic, sendResult.getMessageId());
    }
}
