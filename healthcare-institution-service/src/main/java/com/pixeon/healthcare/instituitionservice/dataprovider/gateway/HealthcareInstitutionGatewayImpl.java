package com.pixeon.healthcare.instituitionservice.dataprovider.gateway;

import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.instituitionservice.adapter.HealthcareInstitutionDTOAdapter;
import com.pixeon.healthcare.instituitionservice.adapter.HealthcareInstitutionEntityAdapter;
import com.pixeon.healthcare.instituitionservice.config.entity.HealthcareInstitution;
import com.pixeon.healthcare.instituitionservice.config.exception.AlreadyExistsCNPJException;
import com.pixeon.healthcare.instituitionservice.dataprovider.repository.HealthcareInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class HealthcareInstitutionGatewayImpl implements HealthcareInstitutionGateway {

    @Autowired
    private HealthcareInstitutionRepository healthcareInstitutionRepository;

    @Override
    public HealthcareInstitutionModel save(HealthcareInstitutionModel healthcareInstitutionModel) {
        try {
            HealthcareInstitution healthcareInstitution = healthcareInstitutionRepository.save(HealthcareInstitutionDTOAdapter.toEntity(healthcareInstitutionModel));
            return HealthcareInstitutionEntityAdapter.toModel(healthcareInstitution);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsCNPJException();
        }
    }
}
