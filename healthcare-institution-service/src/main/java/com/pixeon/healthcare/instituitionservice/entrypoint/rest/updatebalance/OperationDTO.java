package com.pixeon.healthcare.instituitionservice.entrypoint.rest.updatebalance;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OperationDTO {

    private Integer healthcareInstitutionId;
    private String operation;
    private BigDecimal value;

}
