package com.pixeon.healthcare.instituitionservice.dataprovider.gateway;

import com.pixeon.healthcare.domain.usecase.getvalueconfigapplication.ApplicationConfigGateway;
import com.pixeon.healthcare.instituitionservice.dataprovider.api.getvaluefornewhealthcareinstitution.GetValueForNewHealthcareInstitutionOpenFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ApplicationConfigGatewayImpl implements ApplicationConfigGateway {

    @Autowired
    private GetValueForNewHealthcareInstitutionOpenFeign getValueForNewHealthcareInstitutionOpenFeign;

    @Override
    public BigDecimal getValueCreateExam() {
        return null;
    }

    @Override
    public BigDecimal getValueForNewHealthcareInstitution() {
        return getValueForNewHealthcareInstitutionOpenFeign.get().getValue();
    }

    @Override
    public BigDecimal getValueForConsultingExam() {
        return null;
    }
}
