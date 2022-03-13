package com.familyevents.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "com.familyevents.mapper")
public class MybatisConfig {
}
