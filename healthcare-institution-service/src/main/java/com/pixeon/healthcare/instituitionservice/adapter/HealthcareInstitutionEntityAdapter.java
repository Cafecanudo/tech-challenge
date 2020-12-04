package com.pixeon.healthcare.instituitionservice.adapter;

import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.instituitionservice.config.entity.HealthcareInstitution;
import com.pixeon.healthcare.instituitionservice.entrypoint.rest.createInstitution.HealthcareInstitutionDTO;

public class HealthcareInstitutionEntityAdapter {

    public static HealthcareInstitutionDTO toDTO(HealthcareInstitutionModel healthcareInstitutionModel) {
        return HealthcareInstitutionDTO.builder()
                .id(healthcareInstitutionModel.getId())
                .name(healthcareInstitutionModel.getName())
                .cnpj(healthcareInstitutionModel.getCnpj())
                .coin(healthcareInstitutionModel.getCoin())
                .build();
    }

    public static HealthcareInstitutionModel toModel(HealthcareInstitution healthcareInstitution) {
        return HealthcareInstitutionModel.builder()
                .id(healthcareInstitution.getId())
                .name(healthcareInstitution.getName())
                .cnpj(healthcareInstitution.getCnpj())
                .coin(healthcareInstitution.getCoin())
                .build();
    }
}
