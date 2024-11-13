package com.thyro.framework.web.safety.sign;

import java.lang.annotation.*;

/**
 * 签名注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface Sign {
    String signKey() default "s";

    String timeStampKey() default "t";

    int timeout() default 30;

    boolean hideError() default false;
}
