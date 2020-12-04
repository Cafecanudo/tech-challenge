package com.pixeon.healthcare.instituitionservice.entrypoint.rest.createInstitution;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode(of = "cnpj")
public class HealthcareInstitutionDTO {

    private Integer id;
    @NotNull
    @Size(max = 60)
    private String name;
    @NotNull
    @Size(max = 18)
    private String cnpj;
    private BigDecimal coin;

}
