package com.pixeon.healthcare.instituitionservice.config.bean;

import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.CreateHealthcareInstitutionUsecase;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.impl.CreateHealthcareInstitutionUsecaseImpl;
import com.pixeon.healthcare.domain.usecase.getvalueconfigapplication.ApplicationConfigGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateHealthcareInstitutionConfig {

    @Autowired
    public ApplicationConfigGateway applicationConfigGateway;

    @Autowired
    public HealthcareInstitutionGateway healthcareInstitutionGateway;

    @Bean
    public CreateHealthcareInstitutionUsecase createHealthcareInstitutionUsecase() {
        return new CreateHealthcareInstitutionUsecaseImpl(applicationConfigGateway, healthcareInstitutionGateway);
    }
}
