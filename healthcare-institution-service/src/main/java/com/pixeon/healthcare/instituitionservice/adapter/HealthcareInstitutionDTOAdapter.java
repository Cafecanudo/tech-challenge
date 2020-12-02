package com.pixeon.healthcare.instituitionservice.adapter;

import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.instituitionservice.config.entity.HealthcareInstitution;
import com.pixeon.healthcare.instituitionservice.entrypoint.rest.createInstitution.HealthcareInstitutionDTO;

public class HealthcareInstitutionDTOAdapter {

    public static HealthcareInstitutionModel toModel(HealthcareInstitutionDTO healthcareInstitutionDTO) {
        return HealthcareInstitutionModel.builder()
                .name(healthcareInstitutionDTO.getName())
                .cnpj(healthcareInstitutionDTO.getCnpj())
                .coin(healthcareInstitutionDTO.getCoin())
                .build();
    }

    public static HealthcareInstitution toEntity(HealthcareInstitutionModel healthcareInstitutionModel) {
        return HealthcareInstitution.builder()
                .id(healthcareInstitutionModel.getId())
                .name(healthcareInstitutionModel.getName())
                .cnpj(healthcareInstitutionModel.getCnpj())
                .coin(healthcareInstitutionModel.getCoin())
                .build();
    }
}
