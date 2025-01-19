package com.thyro.framework.common.enums;

import com.thyro.framework.common.model.IDict;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 终端的枚举
 */
@RequiredArgsConstructor
@Getter
public enum TerminalEnum implements IDict<Integer> {

    UNKNOWN(0, "未知"), // 目的：在无法解析到 terminal 时，使用它
    WECHAT_MINI_PROGRAM(10, "微信小程序"),
    WECHAT_WAP(11, "微信公众号"),
    H5(20, "H5 网页"),
    APP(31, "手机 App"),
    ;

    TerminalEnum(Integer code, String message) {
        init(code, message);
    }

}
