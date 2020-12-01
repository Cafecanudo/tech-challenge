package com.pixeon.healthcare.usecase.createInstitution;

import com.pixeon.healthcare.domain.factories.HealthcareInstitutionFactoryImpl;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.CreateHealthcareInstitutionService;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.HealthcareInstitutionFactory;
import com.pixeon.healthcare.usecases.getvalueconfigapplication.ApplicationConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateHealthcareInstitutionConfig {

    @Autowired
    private ApplicationConfigFactory applicationConfigFactory;

    @Autowired
    private HealthcareInstitutionFactory healthcareInstitutionFactory;

    @Bean
    public CreateHealthcareInstitutionService createHealthcareInstitutionService() {
        return new CreateHealthcareInstitutionService(applicationConfigFactory, healthcareInstitutionFactory);
    }

    @Bean
    public HealthcareInstitutionFactory createHealthcareInstitutionFactory() {
        return new HealthcareInstitutionFactoryImpl();
    }
}
