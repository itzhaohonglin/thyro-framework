package com.thyro.framework.mysql;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.parser.JsqlParserGlobal;
import com.baomidou.mybatisplus.extension.parser.cache.JdkSerialCaffeineJsqlParseCache;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.thyro.framework.mysql.interceptor.DataOperationInterceptor;
import com.thyro.framework.mysql.shard.MybatisShardStatementInterceptor;
import com.thyro.framework.mysql.type.IDictTypeHandler;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.TimeUnit;

// 目的：先于 MyBatis Plus 自动配置，避免 @MapperScan 可能扫描不到 Mapper 打印 warn 日志
@AutoConfiguration(before = MybatisPlusAutoConfiguration.class)
@MapperScan(value = "${thyro.base-package}", annotationClass = Mapper.class)
// @Import(MybatisShardStatementInterceptor.class)
public class MybatisPlusConfiguration {

    static {
        // 动态 SQL 智能优化支持本地缓存加速解析，更完善的租户复杂 XML 动态 SQL 支持，静态注入缓存
        JsqlParserGlobal.setJsqlParseCache(new JdkSerialCaffeineJsqlParseCache(
                (cache) -> cache.maximumSize(1024)
                        .expireAfterWrite(5, TimeUnit.SECONDS))
        );
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }

    /**
     * 数据自动插入拦截器
     * 自动插入的数据包括：创建人、创建时间、修改人、修改时间
     */
    @Bean
    public DataOperationInterceptor dataOperationInterceptor() {
        return new DataOperationInterceptor();
    }

    /**
     * 注册IDict类型处理器
     * 支持使用枚举充当bean属性，以及Wrapper条件中直接使用枚举
     * 注意会替换默认的枚举处理器，凡是使用枚举当bean属性的在，枚举都需要实现IDict
     */
    @Configuration
    public static class CustomTypeHandlerParser implements ApplicationContextAware {
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) {
            SqlSessionFactory sqlSessionFactory = applicationContext.getBean(SqlSessionFactory.class);
            TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
            typeHandlerRegistry.setDefaultEnumTypeHandler(IDictTypeHandler.class);
        }
    }
}

