package com.thyro.framework.common.enums;

import com.thyro.framework.common.model.IDict;

/**
 * 通用状态枚举
 */
public enum CommonStatusEnum implements IDict<Integer> {
    FAIL(-1, "异常"),
    ENABLE(0, "关闭"),
    DISABLE(1, "开启");


    CommonStatusEnum(Integer code, String message) {
        init(code, message);
    }

    @Override
    public Integer getCode() {
        return IDict.super.getCode();
    }

}
