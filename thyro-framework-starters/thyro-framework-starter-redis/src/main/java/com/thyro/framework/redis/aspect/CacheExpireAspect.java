package com.thyro.framework.redis.aspect;


import com.thyro.framework.redis.annotation.CacheExpire;
import com.thyro.framework.redis.annotation.CacheExpireHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 缓存失效时间Aspect
 * 注意顺序要先于CacheInterceptor，否则过期时间读取不到
 */
@Slf4j
@Aspect
@Component
@Order(-1)
public class CacheExpireAspect {


    /**
     * 设置方法对应的缓存过期时间
     */
    @Around("@annotation(cacheExpire)")
    public Object around(ProceedingJoinPoint joinPoint, CacheExpire cacheExpire) throws Throwable {
        // 过期时间（毫秒）
        long cacheExpiredMilliseconds = 1000L * cacheExpire.value();
        // 上下浮动范围（毫秒）
        long floatRangeMilliseconds = cacheExpiredMilliseconds == 0 ? 0 : (long) (1000 * RandomUtils.nextDouble(0, cacheExpire.floatRange()));
        // 设置失效时间，加上随机浮动值防止缓存穿透
        CacheExpireHolder.set(cacheExpiredMilliseconds + floatRangeMilliseconds);
        try {
            return joinPoint.proceed();
        } finally {
            // 清除对象
            CacheExpireHolder.remove();
        }
    }
}
