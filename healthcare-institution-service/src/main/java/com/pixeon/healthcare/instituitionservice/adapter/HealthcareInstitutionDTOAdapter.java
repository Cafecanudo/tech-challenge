package com.pixeon.healthcare.instituitionservice.adapter;

import com.pixeon.healthcare.domain.model.CoinModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.instituitionservice.config.entity.Coin;
import com.pixeon.healthcare.instituitionservice.config.entity.HealthcareInstitution;
import com.pixeon.healthcare.instituitionservice.config.entity.enums.OperationEnum;
import com.pixeon.healthcare.instituitionservice.entrypoint.rest.createInstitution.HealthcareInstitutionDTO;

import java.util.List;
import java.util.stream.Collectors;

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
                .coins(toCoinsEntity(healthcareInstitutionModel.getCoins()))
                .build();
    }

    private static List<Coin> toCoinsEntity(List<CoinModel> coins) {
        return coins.stream().map(coin -> Coin.builder()
                .id(coin.getId())
                .currentBalance(coin.getCurrentBalance())
                .newBalance(coin.getNewBalance())
                .operation(OperationEnum.valueOf(coin.getOperation().name()))
                .dateOperation(coin.getDateOperation())
                .build()).collect(Collectors.toList());
    }
}
