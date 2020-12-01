package com.pixeon.healthcare.domain.adapter;

import com.pixeon.healthcare.domain.entities.HealthcareInstitution;
import com.pixeon.healthcare.domain.models.HealthcareInstitutionDTO;

public class HealthcareInstitutionAdapter {

    public static HealthcareInstitutionDTO toDTO(HealthcareInstitution healthcareInstitution) {
        return new HealthcareInstitutionDTO.Builder()
                .id(healthcareInstitution.getId())
                .name(healthcareInstitution.getNome())
                .cnpj(healthcareInstitution.getCnpj())
                .coins(healthcareInstitution.getCoin())
                .build();
    }

    public static HealthcareInstitution toEntity(HealthcareInstitutionDTO healthcareInstitutionDTO) {
        return HealthcareInstitution.builder()
                .nome(healthcareInstitutionDTO.getName())
                .cnpj(healthcareInstitutionDTO.getCnpj())
                .coin(healthcareInstitutionDTO.getCoins())
                .build();
    }
}
