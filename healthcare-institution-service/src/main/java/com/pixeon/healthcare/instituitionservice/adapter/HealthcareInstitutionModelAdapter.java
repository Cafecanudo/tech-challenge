package com.pixeon.healthcare.instituitionservice.adapter;

import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.instituitionservice.config.entity.HealthcareInstitution;
import com.pixeon.healthcare.instituitionservice.entrypoint.rest.createInstitution.HealthcareInstitutionDTO;

public class HealthcareInstitutionModelAdapter {

    public static HealthcareInstitutionDTO toDTO(HealthcareInstitutionModel healthcareInstitutionModel) {
        return HealthcareInstitutionDTO.builder()
                .id(healthcareInstitutionModel.getId())
                .name(healthcareInstitutionModel.getName())
                .cnpj(healthcareInstitutionModel.getCnpj())
                .coin(healthcareInstitutionModel.getCoin())
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
