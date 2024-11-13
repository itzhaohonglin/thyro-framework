package com.thyro.framework.web.safety;

import com.thyro.framework.web.safety.sign.SignAspect;
import com.thyro.framework.web.safety.sign.SignExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SignAspect.class, SignExceptionHandler.class})
public class SignAutoConfig {

}
