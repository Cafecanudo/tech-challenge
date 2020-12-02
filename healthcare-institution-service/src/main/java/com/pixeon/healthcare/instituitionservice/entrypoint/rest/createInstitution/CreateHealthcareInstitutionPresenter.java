package com.pixeon.healthcare.instituitionservice.entrypoint.rest.createInstitution;

import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.CreateHealthcareInstitutionUsecase;
import com.pixeon.healthcare.instituitionservice.adapter.HealthcareInstitutionDTOAdapter;
import com.pixeon.healthcare.instituitionservice.adapter.HealthcareInstitutionModelAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateHealthcareInstitutionPresenter {

    @Autowired
    private CreateHealthcareInstitutionUsecase createHealthcareInstitutionUsecase;

    public HealthcareInstitutionDTO create(HealthcareInstitutionDTO healthcareInstitutionDTO) {
        HealthcareInstitutionModel healthcareInstitutionModel = createHealthcareInstitutionUsecase.create(HealthcareInstitutionDTOAdapter.toModel(healthcareInstitutionDTO));
        return HealthcareInstitutionModelAdapter.toDTO(healthcareInstitutionModel);
    }
}
