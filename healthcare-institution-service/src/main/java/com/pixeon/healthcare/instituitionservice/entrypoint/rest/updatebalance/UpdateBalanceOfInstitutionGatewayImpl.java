package com.pixeon.healthcare.instituitionservice.entrypoint.rest.updatebalance;

import com.pixeon.healthcare.domain.config.exception.InstitutionNotFoundException;
import com.pixeon.healthcare.domain.model.CoinModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.usecase.updatebalanceofinstitution.UpdateBalanceOfInstitutionGateway;
import com.pixeon.healthcare.instituitionservice.adapter.CoinModelAdapter;
import com.pixeon.healthcare.instituitionservice.adapter.HealthcareInstitutionEntityAdapter;
import com.pixeon.healthcare.instituitionservice.adapter.HealthcareInstitutionModelAdapter;
import com.pixeon.healthcare.instituitionservice.config.entity.HealthcareInstitution;
import com.pixeon.healthcare.instituitionservice.dataprovider.repository.CoinRepository;
import com.pixeon.healthcare.instituitionservice.dataprovider.repository.HealthcareInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateBalanceOfInstitutionGatewayImpl implements UpdateBalanceOfInstitutionGateway {

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private HealthcareInstitutionRepository healthcareInstitutionRepository;

    @Override
    public HealthcareInstitutionModel getHealthcareInstitutionById(Integer healthcareInstitutionId) {
        Optional<HealthcareInstitution> healthcareInstitution = healthcareInstitutionRepository.findById(healthcareInstitutionId);
        healthcareInstitution.orElseThrow(InstitutionNotFoundException::new);
        return HealthcareInstitutionEntityAdapter.toModel(healthcareInstitution.get());
    }

    @Override
    public void createCoinFromOperation(CoinModel coin) {
        coinRepository.save(CoinModelAdapter.toEntity(coin));
    }

    @Override
    public HealthcareInstitutionModel update(HealthcareInstitutionModel healthcareInstitutionModel) {
        HealthcareInstitution healthcareInstitution = healthcareInstitutionRepository.save(HealthcareInstitutionModelAdapter.toEntity(healthcareInstitutionModel));
        return HealthcareInstitutionEntityAdapter.toModel(healthcareInstitution);
    }
}
