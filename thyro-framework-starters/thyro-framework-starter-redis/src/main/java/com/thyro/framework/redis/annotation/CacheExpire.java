package com.thyro.framework.redis.annotation;


import com.thyro.framework.redis.aspect.CacheExpireAspect;

import java.lang.annotation.*;

/**
 * 缓存过期注解（配合@Cachebale使用）
 * <p>
 * 1.支持防缓存穿透
 * 2.支持方法嵌套（内部方法如果不指定过期时间，则继承外部方法的过期时间）
 * 如果不使用该注解指定@Cacheable的过期时间，将会采取RedisCacheAutoConfiguration中配置的全局缓存失效时间
 *
 * @see CacheExpireAspect
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheExpire {

    /**
     * 失效时间（秒）,负数或0表示永不过期
     * 传参请使用Time类中的静态常量，使语义性更好
     * 示例：@CacheExpire(30 * SECOND)
     */
    int value();

    /**
     * 上下浮动范围（秒）
     * 根据浮动范围生成随机秒数加在缓存失效时间上，防止缓存穿透
     * 默认10毫秒
     */
    double floatRange() default 0.01;

}
