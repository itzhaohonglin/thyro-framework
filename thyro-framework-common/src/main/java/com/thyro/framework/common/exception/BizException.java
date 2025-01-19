package com.thyro.framework.common.exception;


import lombok.Getter;

/**
 * 定义异常接口，有获取状态码和提示消息两个方法，其它所有异常实现该接口；
 */
@Getter
public class BizException extends RuntimeException {
    private final Integer code;
    private final String message;

    public BizException(ResultCode resultCode) {
        super(resultCode.getCode() + resultCode.getText());
        this.code = resultCode.getCode();
        this.message = resultCode.getText();
    }

    public BizException(Integer code, String message) {
        super(code + message);
        this.code = code;
        this.message = message;
    }

    public BizException(String message) {
        super(message);
        this.code = ResultCode.BIZ_ERROR.getCode();
        this.message = message;
    }


}
