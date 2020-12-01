package com.pixeon.healthcare.infraestructure.config;

import com.pixeon.healthcare.domain.factories.ApplicationConfigFactoryImpl;
import com.pixeon.healthcare.usecases.getvalueconfigapplication.ApplicationConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationBean {

    @Bean
    public ApplicationConfigFactory createApplicationConfigFactory() {
        return new ApplicationConfigFactoryImpl();
    }
}
