package com.pixeon.healthcare.instituitionservice.adapter;

import com.pixeon.healthcare.domain.model.CoinModel;
import com.pixeon.healthcare.instituitionservice.config.entity.Coin;
import com.pixeon.healthcare.instituitionservice.config.entity.enums.OperationEnum;

public class CoinModelAdapter {

    public static Coin toEntity(CoinModel coin) {
        return Coin.builder()
                .id(coin.getId())
                .currentBalance(coin.getCurrentBalance())
                .newBalance(coin.getNewBalance())
                .operation(OperationEnum.valueOf(coin.getOperation().name()))
                .valueOperation(coin.getValueOperation())
                .dateOperation(coin.getDateOperation())
                .healthcareInstitution(HealthcareInstitutionModelAdapter.toEntity(coin.getHealthcareInstitution()))
                .build();
    }
}
