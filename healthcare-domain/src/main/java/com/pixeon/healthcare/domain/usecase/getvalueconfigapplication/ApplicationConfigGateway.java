package com.pixeon.healthcare.domain.usecase.getvalueconfigapplication;

import java.math.BigDecimal;

public interface ApplicationConfigGateway {

    BigDecimal getValueCreateExam();

    BigDecimal getValueForNewHealthcareInstitution();

    BigDecimal getValueForConsultingExam();
}
