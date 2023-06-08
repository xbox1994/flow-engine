package com.wty.flowengine.engine.autoconfigure;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.wty.flowengine.engine.ProcessEngine;
import com.wty.flowengine.engine.ProcessEngineConfiguration;
import com.wty.flowengine.engine.runtime.behavior.ActivityBehaviorFactory;
import com.wty.flowengine.engine.runtime.behavior.DefaultActivityBehaviorFactory;
import com.wty.flowengine.engine.runtime.behavior.ServiceTaskBehaviorFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.wty.flowengine.engine.domain.mapper") // note 扫描并注入Bean实例
public class ProcessEngineAutoConfiguration {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // note 了解ObjectProvider<T>的用途
        return interceptor;
    }

    @Bean
    MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
        return properties -> properties.setConfigLocation("classpath:mybatis-config.xml");
    }

    @Bean
    ActivityBehaviorFactory activityBehaviorFactory(ApplicationContext applicationContext) {
        return new DefaultActivityBehaviorFactory(new ServiceTaskBehaviorFactory(applicationContext));
    }

    @Bean
    ProcessEngineConfiguration processEngineConfiguration(SqlSessionFactory sqlSessionFactory,
                                                          PlatformTransactionManager transactionManager,
                                                          ActivityBehaviorFactory activityBehaviorFactory) {
        ProcessEngineConfiguration configuration = new ProcessEngineConfiguration();
        configuration.setSqlSessionTemplate(new SqlSessionTemplate(sqlSessionFactory));
        configuration.setTransactionManager(transactionManager);
        configuration.setActivityBehaviorFactory(activityBehaviorFactory);
        return configuration;
    }

    @Bean
    ProcessEngine processEngine(ProcessEngineConfiguration configuration) {
        return configuration.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
