package com.thyro.framework.web.result;


import com.thyro.framework.common.exception.BizException;
import com.thyro.framework.common.exception.ResultCode;

import java.util.Collection;
import java.util.Objects;

public class Assert {

    public static void isTrue(boolean expression, ResultCode resultCode) {
        if (!expression) {
            throw new BizException(resultCode);
        }
    }

    public static void notTrue(boolean expression, ResultCode resultCode) {
        if (expression) {
            throw new BizException(resultCode);
        }
    }


    public static void isNull(Object object, ResultCode resultCode) {
        if (!Objects.isNull(object)) {
            throw new BizException(resultCode);
        }
    }

    public static void notNull(Object object, ResultCode resultCode) {
        if (Objects.isNull(object)) {
            throw new BizException(resultCode);
        }
    }

    public static void isEmpty(Collection<?> collection, ResultCode resultCode) {
        if (!Objects.isNull(collection) && !collection.isEmpty()) {
            throw new BizException(resultCode);
        }
    }

    public static void notEmpty(Collection<?> collection, ResultCode resultCode) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw new BizException(resultCode);
        }
    }

}
