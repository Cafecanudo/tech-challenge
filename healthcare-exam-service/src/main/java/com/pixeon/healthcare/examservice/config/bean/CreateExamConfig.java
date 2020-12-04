package com.pixeon.healthcare.examservice.config.bean;

import com.pixeon.healthcare.domain.usecase.createexam.CreateExamUsecase;
import com.pixeon.healthcare.domain.usecase.createexam.ExamGateway;
import com.pixeon.healthcare.domain.usecase.createexam.impl.CreateExamUsecaseImpl;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.getvalueconfigapplication.ApplicationConfigGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateExamConfig {

    @Autowired
    public ApplicationConfigGateway applicationConfigGateway;

    @Autowired
    public HealthcareInstitutionGateway healthcareInstitutionGateway;

    @Autowired
    private ExamGateway examGateway;

    @Bean
    public CreateExamUsecase createExamUsecase() {
        return new CreateExamUsecaseImpl(examGateway, healthcareInstitutionGateway, applicationConfigGateway);
    }

}
