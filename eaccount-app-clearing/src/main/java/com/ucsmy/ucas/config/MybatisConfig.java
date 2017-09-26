package com.ucsmy.ucas.config;

import com.ucsmy.core.tool.interceptor.MybatisLogInterceptor;
import com.ucsmy.core.tool.interceptor.MybatisPageInterceptor;
import com.ucsmy.core.tool.serialNumber.interceptor.MybatisSerialNumberExceptionInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Mybatis配置
 * Created by ucs_zhongtingyuan on 2017/4/10.
 */
@Configuration
public class MybatisConfig {

    @Bean("sqlSessionFactory")
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        // 增加拦截器
        MybatisPageInterceptor interceptor = new MybatisPageInterceptor();
        interceptor.setProperties(null);
        MybatisLogInterceptor logInterceptor = new MybatisLogInterceptor();
        bean.setPlugins(new Interceptor[] { interceptor, logInterceptor });

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath:sql/**/*.xml"));
        return bean.getObject();
    }

    @Bean
    public MapperScannerConfigurer configurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setAnnotationClass(Repository.class);
        configurer.setBasePackage("com.ucsmy");
        return configurer;
    }
}
