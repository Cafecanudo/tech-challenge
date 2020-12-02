package com.pixeon.healthcare.applicationservice.entrypoint.rest.getvaluefornewhealthcareinstitution;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ValueForNewHealthcareInstitutionDTO {

    private BigDecimal value;

}
