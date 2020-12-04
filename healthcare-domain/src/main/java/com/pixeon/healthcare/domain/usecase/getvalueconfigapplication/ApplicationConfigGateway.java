package com.pixeon.healthcare.domain.usecase.getvalueconfigapplication;

import java.math.BigDecimal;

public interface ApplicationConfigGateway {

    default BigDecimal getValueCreateExam() {
        throw new RuntimeException("Not implemented");
    }

    default BigDecimal getValueForNewHealthcareInstitution() {
        throw new RuntimeException("Not implemented");
    }

    default BigDecimal getValueForConsultingExam() {
        throw new RuntimeException("Not implemented");
    }
}
