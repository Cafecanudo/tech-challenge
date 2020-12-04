package com.pixeon.healthcare.applicationservice.entrypoint.rest.getvalueforcreatehealthcareinstitution;

import com.pixeon.healthcare.applicationservice.config.entity.Configuration;
import com.pixeon.healthcare.applicationservice.dataprovider.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetValueForCreateHealthcareInstitutionPresenter {

    @Autowired
    private ApplicationRepository applicationRepository;

    public GetValueForCreateHealthcareInstitutionPresenter(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public ValueForNewHealthcareInstitutionDTO get() {
        Configuration configuration = applicationRepository.findAll().iterator().next();
        return ValueForNewHealthcareInstitutionDTO.builder().value(configuration.getValueForNewHealthcareInstitution()).build();
    }
}
