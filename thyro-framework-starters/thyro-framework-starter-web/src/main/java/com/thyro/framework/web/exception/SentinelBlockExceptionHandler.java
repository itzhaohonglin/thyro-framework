package com.thyro.framework.web.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.thyro.framework.common.exception.ResultCode;
import com.thyro.framework.common.result.ResultWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;


@Component
public class SentinelBlockExceptionHandler implements BlockExceptionHandler {


    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        ResultWrapper<Object> fail = ResultWrapper.fail(ResultCode.LOCKED);
        httpServletResponse.setStatus(TOO_MANY_REQUESTS.value());
        httpServletResponse.setHeader("content-type", "application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(fail));
    }
}
