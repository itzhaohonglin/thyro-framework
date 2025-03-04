package com.thyro.framework.web.tools.excel;

import java.lang.annotation.*;

/**
 * 导出表格注解
 * <p>
 * 说明：
 * 许多管理界面查询的结果也要支持导出excel，传统做法需要创建两个方法，但查询逻辑几乎相同，为了解决这个麻烦，开发了此注解
 * 使用：
 * 1.在Controller原来的查询方法上标注@ExportExcel
 * 2.前端在原有查询条件中加入"export=true"即可触发Excel导出
 * 3.在返回类型VO中的属性上使用@Excel注解配置excel需要展示的列
 * 注意：
 * 支持返回结果为List或Page类型的方法，但如果是Page类型，使用导出时会忽略分页参数（等于是查所有）
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportExcel {

    String fileName() default "导出数据";

    String sheetName() default "sheet1";

}
