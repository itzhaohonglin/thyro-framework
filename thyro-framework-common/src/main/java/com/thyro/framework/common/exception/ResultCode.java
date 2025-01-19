package com.thyro.framework.common.exception;

import com.thyro.framework.common.model.IDict;

/**
 * 错误码枚举
 * 0-999 系统异常编码保留
 * <p>
 * 一般情况下，使用 HTTP 响应状态码 https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Status
 * 虽然说，HTTP 响应状态码作为业务使用表达能力偏弱，但是使用在系统层面还是非常不错的
 * 比较特殊的是，因为之前一直使用 0 作为成功，就不使用 200 啦。
 * <p>
 */

public enum ResultCode implements IDict<Integer> {
    SUCCESS(200, "成功"),
    // ========== 客户端错误段 ==========

    BAD_REQUEST(400, "参数不合法"),
    UNAUTHORIZED(401, "未登录"),
    FORBIDDEN(403, "暂无权限"),
    NOT_FOUND(404, "资源未找到"),
    METHOD_NOT_ALLOWED(405, "请求方法不正确"),
    REQUEST_TIMEOUT(408, "请求超时"),
    LOCKED(423, "请求失败，请稍后重试"), // 并发请求，不允许
    TOO_MANY_REQUESTS(429, "请求过于频繁，请稍后重试"),

    // ========== 服务端错误段 ==========

    SYSTEM_ERROR(500, "系统异常"),
    NOT_IMPLEMENTED(501, "服务器不支持的处理"),
    ERROR_CONFIGURATION(502, "错误的配置项"),
    SERVICE_UNAVAILABLE(503, "服务器维护中"),

    // ========== 自定义错误段 ==========
    BIZ_ERROR(900, "业务异常"),
    REPEATED_REQUESTS(901, "重复请求，请稍后重试"),
    DEMO_DENY(902, "演示模式，禁止写操作"),
    UNKNOWN(999, "未知错误"),
    ;

    ResultCode(Integer code, String message) {
        init(code, message);
    }
}