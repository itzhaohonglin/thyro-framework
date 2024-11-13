package com.thyro.framework.feign.exception;

/**
 * Feign调用异常
 * 用于包装远程调用时出现的系统异常
 *
 */
public class FeignException extends RuntimeException {

    public FeignException() {
        super();
    }

    public FeignException(String message) {
        super(message);
    }

    public FeignException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeignException(Throwable cause) {
        super(cause);
    }
}
