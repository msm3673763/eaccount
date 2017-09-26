package com.ucsmy.eaccount.pay.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

/**
 * Mybatis配置
 * Created by ucs_zhongtingyuan on 2017/4/10.
 */
@Configuration
public class MybatisConfig {

    @Bean
    public MapperScannerConfigurer configurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setAnnotationClass(Repository.class);
        configurer.setBasePackage("com.ucsmy.eaccount.pay.dao");
        return configurer;
    }
}
