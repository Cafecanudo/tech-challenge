package com.pixeon.healthcare.instituitionservice.dataprovider.gateway;

import com.pixeon.healthcare.domain.usecase.getvalueconfigapplication.ApplicationConfigGateway;
import com.pixeon.healthcare.instituitionservice.dataprovider.api.applicationservice.ApplicationServiceOpenFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ApplicationConfigGatewayImpl implements ApplicationConfigGateway {

    @Autowired
    private ApplicationServiceOpenFeign applicationServiceOpenFeign;

    @Override
    public BigDecimal getValueForNewHealthcareInstitution() {
        return applicationServiceOpenFeign.getValueForCreateHealthcareInstitution().getValue();
    }

}
