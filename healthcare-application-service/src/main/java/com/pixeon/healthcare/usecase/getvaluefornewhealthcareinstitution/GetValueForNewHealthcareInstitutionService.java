package com.pixeon.healthcare.usecase.getvaluefornewhealthcareinstitution;

import com.pixeon.healthcare.domain.entities.Configuration;
import com.pixeon.healthcare.infraestructure.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetValueForNewHealthcareInstitutionService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public GetValueForNewHealthcareInstitutionService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public ValueForNewHealthcareInstitutionDTO get() {
        Configuration configuration = applicationRepository.findAll().iterator().next();
        return ValueForNewHealthcareInstitutionDTO.builder().value(configuration.getValueForNewHealthcareInstitution()).build();
    }
}
