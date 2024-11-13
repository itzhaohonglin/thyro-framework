package com.thyro.framework.web.transform.unwrapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thyro.framework.common.result.ResultWrapper;

/**
 * ResultWrapper的解包器
 */
public class ResultUnWrapper implements UnWrapper<ResultWrapper<?>> {
    @Override
    public Object unWrap(ResultWrapper<?> source) {
        Object data = source.getData();
        if (data instanceof IPage) {
            return ((IPage<?>) data).getRecords();
        }
        // List或bean直接返回
        return data;
    }
}