package com.thyro.framework.web.safety.repeat;


import com.alibaba.fastjson.JSON;
import com.thyro.framework.common.context.UserContext;
import com.thyro.framework.common.exception.ResultCode;
import com.thyro.framework.common.exception.BizException;
import com.thyro.framework.redis.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * 防止重复提交aop
 */
@Aspect
@ConditionalOnBean(RedisClient.class)
@Slf4j
public class UnRepeatableRequestAspect {

    private static final String REPEAT_REQUEST_REDIS_KEY = "repeat_request:";

    @Resource
    private RedisClient redisClient;

    @Around("@annotation(com.thyro.framework.web.safety.repeat.UnRepeatable)")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {

        Long userId = UserContext.getUserId();
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        String requestInfo = joinPoint.getTarget() + method.getName() + userId + JSON.toJSONString(args);

        String md5 = DigestUtils.md5DigestAsHex(requestInfo.getBytes());
        String lockKey = REPEAT_REQUEST_REDIS_KEY.concat(md5);
        boolean lockFlag = false;
        try {
            lockFlag = redisClient.setNx(lockKey, md5);
            if (Boolean.TRUE.equals(lockFlag)) {
                return joinPoint.proceed();
            } else {
                throw new BizException(ResultCode.REPEATED_REQUESTS);
            }
        } finally {
            if (Boolean.TRUE.equals(lockFlag)) {
                redisClient.delete(lockKey);
            }
        }
    }
}
