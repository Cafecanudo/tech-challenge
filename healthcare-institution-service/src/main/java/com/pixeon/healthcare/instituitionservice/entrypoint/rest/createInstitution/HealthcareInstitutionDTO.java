package com.pixeon.healthcare.instituitionservice.entrypoint.rest.createInstitution;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode(of = "cnpj")
public class HealthcareInstitutionDTO {

    private int id;
    private String name;
    private String cnpj;
    private BigDecimal coin;

}
