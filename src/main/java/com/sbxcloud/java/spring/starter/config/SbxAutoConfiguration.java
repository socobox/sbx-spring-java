package com.sbxcloud.java.spring.starter.config;

import com.sbxcloud.java.spring.starter.sbxcore.SbxCore;
import com.sbxcloud.java.spring.starter.sbxcore.dao.SbxCoreRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(SbxCore.class)
@EnableConfigurationProperties(SbxProperties.class)
public class SbxAutoConfiguration {


    @Autowired
    private SbxProperties sbxProperties;


    @Bean
    @ConditionalOnMissingBean
    public SbxCore configure() {
        return new SbxCore(sbxProperties.getDomain(), sbxProperties.getAppKey(), new SbxCoreRepositoryImpl());
    }

}
