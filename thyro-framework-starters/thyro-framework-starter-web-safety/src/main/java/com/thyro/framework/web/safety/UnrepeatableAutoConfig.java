package com.thyro.framework.web.safety;


import com.thyro.framework.redis.RedisClient;
import com.thyro.framework.web.safety.repeat.UnRepeatableRequestAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnBean(RedisClient.class)
@Import({UnRepeatableRequestAspect.class})
public class UnrepeatableAutoConfig {
}
