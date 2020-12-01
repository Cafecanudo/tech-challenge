package com.pixeon.healthcare.domain.factories;

import com.pixeon.healthcare.domain.adapter.HealthcareInstitutionAdapter;
import com.pixeon.healthcare.domain.config.exception.CNPJJaExisteException;
import com.pixeon.healthcare.domain.entities.HealthcareInstitution;
import com.pixeon.healthcare.domain.models.HealthcareInstitutionDTO;
import com.pixeon.healthcare.usecase.createInstitution.HealthcareInstitutionRepository;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.HealthcareInstitutionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

public class HealthcareInstitutionFactoryImpl implements HealthcareInstitutionFactory {

    @Autowired
    private HealthcareInstitutionRepository healthcareInstitutionRepository;

    @Override
    public HealthcareInstitutionDTO save(HealthcareInstitutionDTO healthcareInstitutionDTO) {
        try {
            HealthcareInstitution healthcareInstitution = healthcareInstitutionRepository.save(HealthcareInstitutionAdapter.toEntity(healthcareInstitutionDTO));
            return HealthcareInstitutionAdapter.toDTO(healthcareInstitution);
        } catch (DataIntegrityViolationException e) {
            throw new CNPJJaExisteException();
        }
    }

    @Override
    public HealthcareInstitutionDTO getCurrentInstitution() {
        return null;
    }

    @Override
    public HealthcareInstitutionDTO getInstitutionForExamBy(Integer examId) {
        return null;
    }

    @Override
    public void update(HealthcareInstitutionDTO institution) {

    }
}
