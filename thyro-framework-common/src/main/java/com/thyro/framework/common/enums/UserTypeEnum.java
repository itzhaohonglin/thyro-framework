package com.thyro.framework.common.enums;

import com.thyro.framework.common.model.IDict;
import lombok.AllArgsConstructor;

/**
 * 全局用户类型枚举
 */
@AllArgsConstructor
public enum UserTypeEnum implements IDict<Integer> {

    MEMBER(1, "会员"), // 面向 c 端，普通用户
    ADMIN(2, "管理员"); // 面向 b 端，管理后台

    UserTypeEnum(Integer code, String message) {
        init(code, message);
    }
}
