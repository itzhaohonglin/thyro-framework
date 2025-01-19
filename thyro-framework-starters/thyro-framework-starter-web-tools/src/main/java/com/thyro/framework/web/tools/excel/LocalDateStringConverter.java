package com.thyro.framework.web.tools.excel;


import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.thyro.framework.common.utils.DateUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LocalDateStringConverter implements Converter<LocalDateTime> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(ReadCellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
        return LocalDateTime.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern(DateUtil.DATETIME_PATTERN));
    }

    @Override
    public WriteCellData<?> convertToExcelData(LocalDateTime localDateTime, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.DATETIME_PATTERN);
        String format = formatter.format(localDateTime);
        return new WriteCellData<>(format);
    }
}
