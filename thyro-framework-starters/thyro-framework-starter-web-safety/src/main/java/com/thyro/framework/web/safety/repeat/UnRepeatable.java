package com.thyro.framework.web.safety.repeat;

import java.lang.annotation.*;

/**
 * 防止重复提交
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface UnRepeatable {

}
