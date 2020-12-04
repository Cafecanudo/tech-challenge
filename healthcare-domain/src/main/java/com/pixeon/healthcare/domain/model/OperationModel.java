package com.pixeon.healthcare.domain.model;

import com.pixeon.healthcare.domain.model.enums.OperationEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OperationModel {

    private Integer healthcareInstitutionId;
    private OperationEnum operation;
    private BigDecimal value;

    public boolean isDebit() {
        return operation.equals(OperationEnum.DEBIT);
    }

    public boolean isNegative() {
        return value.doubleValue() < 0.0;
    }
}
