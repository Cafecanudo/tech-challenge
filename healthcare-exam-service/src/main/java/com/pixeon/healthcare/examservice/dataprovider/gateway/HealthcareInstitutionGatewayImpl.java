package com.pixeon.healthcare.examservice.dataprovider.gateway;

import com.pixeon.healthcare.domain.config.exception.InstitutionNotFoundException;
import com.pixeon.healthcare.domain.config.exception.NegativeValuesNotAcceptedException;
import com.pixeon.healthcare.domain.config.exception.NoBalanceToCreateExamException;
import com.pixeon.healthcare.domain.model.CoinModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.examservice.dataprovider.api.institutionservice.InstitutionServiceOpenFeign;
import com.pixeon.healthcare.examservice.dataprovider.api.institutionservice.OperationDTO;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class HealthcareInstitutionGatewayImpl implements HealthcareInstitutionGateway {

    @Autowired
    private InstitutionServiceOpenFeign institutionServiceOpenFeign;

    @Override
    public HealthcareInstitutionModel getCurrentInstitution() {
        return RecoveryInstituitionFromTokemJWT.recovery();
    }

    @Override
    public void updateBalance(HealthcareInstitutionModel institution, CoinModel coin) {
        try {
            institutionServiceOpenFeign.updateBalance(OperationDTO.builder()
                    .healthcareInstitutionId(institution.getId())
                    .operation(coin.getOperation().name())
                    .value(coin.getValueOperation())
                    .build());
        } catch (FeignException feignException) {
            throwExceptionWhenDontHaveEnoughBalance(feignException);
            throwExceptionWhenValueReportedNegative(feignException);
            throwExceptionWhenInstitutionNotFound(feignException);
        }
    }

    private void throwExceptionWhenInstitutionNotFound(FeignException feignException) {
        if (feignException.status() == HttpStatus.NOT_FOUND.value()) {
            throw new InstitutionNotFoundException();
        }
    }

    private void throwExceptionWhenValueReportedNegative(FeignException feignException) {
        if (feignException.status() == HttpStatus.BAD_REQUEST.value()) {
            throw new NegativeValuesNotAcceptedException();
        }
    }

    private void throwExceptionWhenDontHaveEnoughBalance(FeignException feignException) {
        if (feignException.status() == HttpStatus.NOT_MODIFIED.value()) {
            throw new NoBalanceToCreateExamException();
        }
    }
}
