package com.thyro.framework.feign;

import com.thyro.framework.common.exception.BizException;
import com.thyro.framework.common.result.ResultWrapper;
import com.thyro.framework.feign.exception.FeignException;
import feign.codec.DecodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.thyro.framework.common.exception.ResultCode;

/**
 * 远程调用异常处理
 */
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FeignExceptionHandler {
    /**
     * feign异常（远程服务抛出的异常）
     */
    @ResponseBody
    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultWrapper<Void> handleException(FeignException exception) {
        // 打印堆栈信息
        log.error("❌远程调用时发生系统异常，ℹ原始异常信息：【{}】", exception.getMessage());
        return ResultWrapper.fail(ResultCode.SYSTEM_ERROR, "原始异常信息：" + exception.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(DecodeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultWrapper<Void> handleException(DecodeException exception) {
        if (exception.getCause() instanceof BizException) {
            // 业务异常，包装返回
            BizException bizException = (BizException) exception.getCause();
            return ResultWrapper.fail(bizException.getCode(), bizException.getMessage());
        }
        // 否则打印错误日志并返回
        log.error("❌远程调用结束解析返回值时出现异常", exception);
        return ResultWrapper.fail(ResultCode.SYSTEM_ERROR, exception.getMessage());
    }

}
