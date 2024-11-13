package com.thyro.framework.web.transform.unwrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * mybatis-plus的IPage解包器
 *
 */
public class PageUnWrapper<T> implements UnWrapper<IPage<T>> {

    @Override
    public List<T> unWrap(IPage<T> source) {
        return source.getRecords();
    }
}

