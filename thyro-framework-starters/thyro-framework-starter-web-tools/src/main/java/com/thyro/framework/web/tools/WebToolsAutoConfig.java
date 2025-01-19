package com.thyro.framework.web.tools;

import com.thyro.framework.web.tools.excel.ExportExcelAspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ExportExcelAspect.class)
public class WebToolsAutoConfig {
}
