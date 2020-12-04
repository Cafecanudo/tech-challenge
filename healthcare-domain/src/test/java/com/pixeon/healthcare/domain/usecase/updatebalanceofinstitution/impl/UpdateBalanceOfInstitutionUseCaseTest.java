package com.pixeon.healthcare.domain.usecase.updatebalanceofinstitution.impl;

import com.pixeon.healthcare.domain.config.exception.NegativeValuesNotAcceptedException;
import com.pixeon.healthcare.domain.config.exception.NoBalanceToCreateExamException;
import com.pixeon.healthcare.domain.model.CoinModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.model.OperationModel;
import com.pixeon.healthcare.domain.model.enums.OperationEnum;
import com.pixeon.healthcare.domain.usecase.updatebalanceofinstitution.UpdateBalanceOfInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.updatebalanceofinstitution.UpdateBalanceOfInstitutionUseCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class UpdateBalanceOfInstitutionUseCaseTest {

    public static final int HEALTHCARE_INSTITUTION_ID = 1;
    @Mock
    private UpdateBalanceOfInstitutionGateway updateBalanceOfInstitutionGateway;

    private UpdateBalanceOfInstitutionUseCase updateBalanceOfInstitutionUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.updateBalanceOfInstitutionUseCase = new UpdateBalanceOfInstitutionUseCaseImpl(updateBalanceOfInstitutionGateway);
        when(updateBalanceOfInstitutionGateway.getHealthcareInstitutionById(anyInt())).thenReturn(HealthcareInstitutionModel.builder()
                .coin(new BigDecimal(20.0))
                .build());

        when(updateBalanceOfInstitutionGateway.update(any())).then((Answer<HealthcareInstitutionModel>) invocationOnMock -> invocationOnMock.getArgument(0));
    }

    @Test
    public void shouldUpdateBalanceInstitution() {
        HealthcareInstitutionModel healthcareInstitutionModel = this.updateBalanceOfInstitutionUseCase.update(OperationModel.builder()
                .healthcareInstitutionId(HEALTHCARE_INSTITUTION_ID)
                .operation(OperationEnum.DEBIT)
                .value(new BigDecimal(2.0))
                .build());

        assertNotNull(healthcareInstitutionModel);
    }

    @Test
    public void shouldCalculateNewValueForInstitutionWhenDebit() {
        HealthcareInstitutionModel healthcareInstitutionModel = this.updateBalanceOfInstitutionUseCase.update(OperationModel.builder()
                .healthcareInstitutionId(HEALTHCARE_INSTITUTION_ID)
                .operation(OperationEnum.DEBIT)
                .value(new BigDecimal(1.9))
                .build());

        assertEquals(18.1, healthcareInstitutionModel.getCoin().doubleValue(), 0.1);
    }

    @Test
    public void shouldCalculateNewValueForInstitutionCredit() {
        when(updateBalanceOfInstitutionGateway.getHealthcareInstitutionById(anyInt())).thenReturn(HealthcareInstitutionModel.builder()
                .coin(new BigDecimal(0.0))
                .build());

        HealthcareInstitutionModel healthcareInstitutionModel = this.updateBalanceOfInstitutionUseCase.update(OperationModel.builder()
                .healthcareInstitutionId(HEALTHCARE_INSTITUTION_ID)
                .operation(OperationEnum.CREDIT)
                .value(new BigDecimal(23.78))
                .build());

        assertEquals(23.78, healthcareInstitutionModel.getCoin().doubleValue(), 0.1);
    }

    @Test
    public void shouldReturnWhenInstitutionEnoughBalance() {
        when(updateBalanceOfInstitutionGateway.getHealthcareInstitutionById(anyInt())).thenReturn(HealthcareInstitutionModel.builder()
                .coin(new BigDecimal(1.0))
                .build());

        assertThrows(NoBalanceToCreateExamException.class, () -> this.updateBalanceOfInstitutionUseCase.update(OperationModel.builder()
                .healthcareInstitutionId(HEALTHCARE_INSTITUTION_ID)
                .operation(OperationEnum.DEBIT)
                .value(new BigDecimal(1.9))
                .build()));
    }

    @Test
    public void shouldAcceptNegativeValues() {
        when(updateBalanceOfInstitutionGateway.getHealthcareInstitutionById(anyInt())).thenReturn(HealthcareInstitutionModel.builder()
                .coin(new BigDecimal(100.0))
                .build());

        assertThrows(NegativeValuesNotAcceptedException.class, () -> this.updateBalanceOfInstitutionUseCase.update(OperationModel.builder()
                .healthcareInstitutionId(HEALTHCARE_INSTITUTION_ID)
                .operation(OperationEnum.DEBIT)
                .value(new BigDecimal(-1.9))
                .build()));
    }

    @Test
    public void verifyIfUpdateBalanceItsCallingUpdate() {
        this.updateBalanceOfInstitutionUseCase.update(OperationModel.builder()
                .healthcareInstitutionId(HEALTHCARE_INSTITUTION_ID)
                .operation(OperationEnum.DEBIT)
                .value(new BigDecimal(1.9))
                .build());

        verify(updateBalanceOfInstitutionGateway, times(1))
                .update(HealthcareInstitutionModel.builder().coin(new BigDecimal(18.1)).build());
    }

    @Test
    public void verifyIfUpdateBalanceItsCreatingCoin() {
        when(updateBalanceOfInstitutionGateway.getHealthcareInstitutionById(anyInt())).thenReturn(HealthcareInstitutionModel.builder()
                .coin(new BigDecimal(20.0))
                .build());

        this.updateBalanceOfInstitutionUseCase.update(OperationModel.builder()
                .healthcareInstitutionId(HEALTHCARE_INSTITUTION_ID)
                .operation(OperationEnum.DEBIT)
                .value(new BigDecimal(1.9))
                .build());

        verify(updateBalanceOfInstitutionGateway, times(1)).createCoinFromOperation(any(CoinModel.class));
    }
}