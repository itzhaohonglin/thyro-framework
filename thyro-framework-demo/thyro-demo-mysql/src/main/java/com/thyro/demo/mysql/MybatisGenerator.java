package com.thyro.demo.mysql;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.query.SQLQuery;

import java.nio.file.Paths;
import java.sql.Types;

public class MybatisGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/thyro_mysql_demo?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8&remarks=true&useInformationSchema=true", "root", "root")
                // 全局配置
                .globalConfig(builder -> builder.author("zhaohonglin").outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java").commentDate("yyyy-MM-dd"))
                // 数据源配置
                .dataSourceConfig(builder -> builder
                        .databaseQueryClass(SQLQuery.class)
                        .typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        }).dbQuery(new MySqlQuery())
                )
                // 包配置
                .packageConfig(builder -> builder
                        .parent("com.thyro.demo")
                        .moduleName("mysql")
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .addInclude("test1,test2") // 设置需要生成的表名
                        .entityBuilder()
                        .enableLombok()
                        .enableFileOverride()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
