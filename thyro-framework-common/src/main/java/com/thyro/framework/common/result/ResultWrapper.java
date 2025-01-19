package com.thyro.framework.common.result;

import com.thyro.framework.common.exception.ResultCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

/**
 * 响应结果包装类
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class ResultWrapper<T> {
    /**
     * 结果码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 链路id
     */
    private String traceId;
    /**
     * 返回数据内容
     */
    private T data;


    public ResultWrapper(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.setTraceId();
    }

    public ResultWrapper(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getText());
    }

    public ResultWrapper(ResultCode resultCode, T data) {
        this(resultCode);
        this.data = data;
    }


    public static <T> ResultWrapper<T> success(T data) {
        return new ResultWrapper<>(ResultCode.SUCCESS, data);
    }

    public static <T> ResultWrapper<T> success() {
        return new ResultWrapper<>(ResultCode.SUCCESS);
    }


    public static ResultWrapper<Void> fail(ResultCode resultCode, String detailErrorMsg) {
        return new ResultWrapper<>(resultCode.getCode(), resultCode.getText() + "：" + detailErrorMsg);
    }

    public static <T> ResultWrapper<T> fail(ResultCode resultCode) {
        return new ResultWrapper<>(resultCode.getCode(), resultCode.getText());
    }

    public static <T> ResultWrapper<T> fail(Integer code, String message) {
        return new ResultWrapper<>(code, message);
    }

    @Trace
    public void setTraceId() {
        this.traceId = TraceContext.traceId();
    }

}
