package com.thyro.framework.web.transform.transformer;


import com.thyro.framework.common.model.IDict;
import com.thyro.framework.web.transform.annotation.TransformEnum;

import javax.validation.constraints.NotNull;


/**
 * 枚举转换器
 */
public class EnumTransformer<T> implements Transformer<T, TransformEnum> {

    @Override
    @SuppressWarnings("unchecked")
    public String transform(@NotNull T enumCode, @NotNull TransformEnum annotation) {
        return IDict.getTextByCode((Class<? extends IDict<T>>) annotation.value(), enumCode);
    }


}
