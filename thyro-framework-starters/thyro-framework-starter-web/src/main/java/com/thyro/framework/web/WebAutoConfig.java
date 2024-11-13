package com.thyro.framework.web;


import com.thyro.framework.web.config.DateTimeConfig;
import com.thyro.framework.web.config.DateTimeConvertPrimaryConfig;
import com.thyro.framework.web.exception.GlobalExceptionHandler;
import com.thyro.framework.web.exception.SentinelBlockExceptionHandler;
import com.thyro.framework.web.result.InitializingAdviceDecorator;
import com.thyro.framework.web.swagger.SwaggerConfiguration;
import com.thyro.framework.web.swagger.SwaggerShortcutController;
import com.thyro.framework.web.transform.TransformConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.thyro.framework.**.web")
@Import({SwaggerConfiguration.class, InitializingAdviceDecorator.class,
        GlobalExceptionHandler.class, SentinelBlockExceptionHandler.class,
        DateTimeConfig.class, SwaggerShortcutController.class, TransformConfig.class, DateTimeConvertPrimaryConfig.class})
public class WebAutoConfig {

}
