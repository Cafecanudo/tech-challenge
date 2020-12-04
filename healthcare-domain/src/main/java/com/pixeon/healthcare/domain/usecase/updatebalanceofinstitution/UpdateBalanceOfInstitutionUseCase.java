package com.pixeon.healthcare.domain.usecase.updatebalanceofinstitution;

import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.model.OperationModel;

@FunctionalInterface
public interface UpdateBalanceOfInstitutionUseCase {

    HealthcareInstitutionModel update(OperationModel operationModel);
}
