package com.pixeon.healthcare.instituitionservice.adapter;

import com.pixeon.healthcare.domain.model.OperationModel;
import com.pixeon.healthcare.domain.model.enums.OperationEnum;
import com.pixeon.healthcare.instituitionservice.entrypoint.rest.updatebalance.OperationDTO;

public class OperationDTOAdapter {

    public static OperationModel toModel(OperationDTO operationDTO) {
        return OperationModel.builder()
                .healthcareInstitutionId(operationDTO.getHealthcareInstitutionId())
                .operation(OperationEnum.valueOf(operationDTO.getOperation()))
                .value(operationDTO.getValue())
                .build();
    }
}
