package com.pixeon.healthcare.domain.model;

import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.ValidateCNPJ;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(of = "cnpj")
public class HealthcareInstitutionModel {

    private Integer id;
    private String name;
    private String cnpj;
    private BigDecimal coin;
    private List<CoinModel> coins;

    public BigDecimal subtractCoins(BigDecimal value) {
        return this.coin.subtract(value);
    }

    public boolean examOwner(ExamModel examModel) {
        if (examModel == null) {
            return false;
        }
        return equals(examModel.getHealthcareInstitution());
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
