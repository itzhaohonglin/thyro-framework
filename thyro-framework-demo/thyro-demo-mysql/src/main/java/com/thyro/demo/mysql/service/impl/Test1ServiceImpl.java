package com.thyro.demo.mysql.service.impl;

import com.thyro.demo.mysql.entity.Test1;
import com.thyro.demo.mysql.mapper.Test1Mapper;
import com.thyro.demo.mysql.service.ITest1Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试1表 服务实现类
 * </p>
 *
 * @since 2025-01-18
 */
@Service
public class Test1ServiceImpl extends ServiceImpl<Test1Mapper, Test1> implements ITest1Service {

}
