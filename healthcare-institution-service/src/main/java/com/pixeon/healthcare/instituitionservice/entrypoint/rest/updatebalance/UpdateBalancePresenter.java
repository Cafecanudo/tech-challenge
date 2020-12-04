package com.pixeon.healthcare.instituitionservice.entrypoint.rest.updatebalance;

import com.pixeon.healthcare.domain.usecase.updatebalanceofinstitution.UpdateBalanceOfInstitutionUseCase;
import com.pixeon.healthcare.instituitionservice.adapter.OperationDTOAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateBalancePresenter {

    @Autowired
    private UpdateBalanceOfInstitutionUseCase updateBalanceOfInstitutionUseCase;

    public void update(OperationDTO operationDTO) {
        updateBalanceOfInstitutionUseCase.update(OperationDTOAdapter.toModel(operationDTO));
    }
}
