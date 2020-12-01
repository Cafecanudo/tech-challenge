package com.pixeon.healthcare.domain.factories;

import com.pixeon.healthcare.usecase.getValueForNewHealtcareInstution.GetValueForNewHealthcareInstitutionOpenFeign;
import com.pixeon.healthcare.usecases.getvalueconfigapplication.ApplicationConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class ApplicationConfigFactoryImpl implements ApplicationConfigFactory {

    @Autowired
    private GetValueForNewHealthcareInstitutionOpenFeign valueForNewHealthcareInstitutionOpenFeign;

    @Override
    public BigDecimal getValueCreateExam() {
        return null;
    }

    @Override
    public BigDecimal getValueForNewHealthcareInstitution() {
        return valueForNewHealthcareInstitutionOpenFeign.get().getValue();
    }

    @Override
    public BigDecimal getValueForConsultingExam() {
        return null;
    }
}
