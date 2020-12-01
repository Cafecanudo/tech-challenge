package com.pixeon.healthcare.domain.entities;

import com.pixeon.healthcare.domain.usecases.createhealthcareinstitution.ValidateCNPJ;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode(of = "cnpj")
public class HealthcareInstitution {

    private int id;
    private String name;
    private String cnpj;
    private BigDecimal coin;

    public void subtractCoins(BigDecimal value) {
        this.setCoin(this.coin.subtract(value));
    }

    public boolean examOwner(Exam exam) {
        if (exam == null) {
            return false;
        }
        return equals(exam.getHealthcareInstitution());
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
