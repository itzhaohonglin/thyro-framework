package com.thyro.framework.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;


public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("feign", String.valueOf(true));
    }
}
