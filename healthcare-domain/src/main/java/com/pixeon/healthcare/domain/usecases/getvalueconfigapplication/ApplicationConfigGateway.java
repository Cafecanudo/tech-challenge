package com.pixeon.healthcare.domain.usecases.getvalueconfigapplication;

import java.math.BigDecimal;

public interface ApplicationConfigGateway {

    BigDecimal getValueCreateExam();

    BigDecimal getValueForNewHealthcareInstitution();

    BigDecimal getValueForConsultingExam();
}
