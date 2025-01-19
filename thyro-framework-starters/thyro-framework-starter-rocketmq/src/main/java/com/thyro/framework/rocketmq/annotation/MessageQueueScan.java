package com.thyro.framework.rocketmq.annotation;


import com.thyro.framework.rocketmq.spring.MessageQueueRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MessageQueueRegistrar.class)
public @interface MessageQueueScan {

    String [] value();
}
