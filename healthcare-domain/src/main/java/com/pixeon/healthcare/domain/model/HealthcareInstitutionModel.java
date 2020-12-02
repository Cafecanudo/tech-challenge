package com.pixeon.healthcare.domain.model;

import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.ValidateCNPJ;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode(of = "cnpj")
public class HealthcareInstitutionModel {

    private int id;
    private String name;
    private String cnpj;
    private BigDecimal coin;

    public void subtractCoins(BigDecimal value) {
        this.setCoin(this.coin.subtract(value));
    }

    public boolean examOwner(ExamModel examModel) {
        if (examModel == null) {
            return false;
        }
        return equals(examModel.getHealthcareInstitutionModel());
    }

    public boolean isEmptyOrBlank() {
        return getName() == null || getName().isEmpty();
    }

    public boolean isEmptyOrBlankCNPJ() {
        return getCnpj() == null || getCnpj().isEmpty();
    }

    public boolean isValidCNPJ() {
        return ValidateCNPJ.isCNPJ(getCnpj());
    }
}
