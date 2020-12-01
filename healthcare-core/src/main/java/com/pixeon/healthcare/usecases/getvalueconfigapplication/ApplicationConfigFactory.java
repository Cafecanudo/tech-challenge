package com.pixeon.healthcare.usecases.getvalueconfigapplication;

import java.math.BigDecimal;

public interface ApplicationConfigFactory {

    BigDecimal getValueCreateExam();

    BigDecimal getValueForNewHealthcareInstitution();

    BigDecimal getValueForConsultingExam();
}
