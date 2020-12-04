package com.pixeon.healthcare.instituitionservice.config.bean;

import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.CreateHealthcareInstitutionUsecase;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.impl.CreateHealthcareInstitutionUsecaseImpl;
import com.pixeon.healthcare.domain.usecase.getvalueconfigapplication.ApplicationConfigGateway;
import com.pixeon.healthcare.domain.usecase.updatebalanceofinstitution.UpdateBalanceOfInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.updatebalanceofinstitution.UpdateBalanceOfInstitutionUseCase;
import com.pixeon.healthcare.domain.usecase.updatebalanceofinstitution.impl.UpdateBalanceOfInstitutionUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateHealthcareInstitutionConfig {

    @Autowired
    public ApplicationConfigGateway applicationConfigGateway;

    @Autowired
    public HealthcareInstitutionGateway healthcareInstitutionGateway;

    @Autowired
    public UpdateBalanceOfInstitutionGateway updateBalanceOfInstitutionGateway;

    @Bean
    public CreateHealthcareInstitutionUsecase createHealthcareInstitutionUsecase() {
        return new CreateHealthcareInstitutionUsecaseImpl(applicationConfigGateway, healthcareInstitutionGateway);
    }

    @Bean
    public UpdateBalanceOfInstitutionUseCase createUpdateBalanceOfInstitutionUseCase() {
        return new UpdateBalanceOfInstitutionUseCaseImpl(updateBalanceOfInstitutionGateway);
    }
}
