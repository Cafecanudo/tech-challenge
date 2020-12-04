package com.pixeon.healthcare.examservice.dataprovider.gateway;

import com.pixeon.healthcare.domain.usecase.getvalueconfigapplication.ApplicationConfigGateway;
import com.pixeon.healthcare.examservice.dataprovider.api.applicationservice.ApplicationServiceOpenFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ApplicationConfigGatewayImpl implements ApplicationConfigGateway {

    @Autowired
    private ApplicationServiceOpenFeign applicationServiceOpenFeign;

    @Override
    public BigDecimal getValueCreateExam() {
        return applicationServiceOpenFeign.getValueForCreateExam().getValue();
    }

    @Override
    public BigDecimal getValueForConsultingExam() {
        return applicationServiceOpenFeign.getValueForConsultingExam().getValue();
    }
}
