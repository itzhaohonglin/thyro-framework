package com.thyro.framework.web.transform.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})
public @interface LocalCache {
    Class<?> value();
}
