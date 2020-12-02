package com.pixeon.healthcare.domain.usecase.createexam;

import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;

import java.math.BigDecimal;

public class CheckBalanceInstitution<T extends RuntimeException> {

    private static final double WITHOUT_BALANCE = 0.0;
    private T genericClass;

    public CheckBalanceInstitution(T genericClass) {
        this.genericClass = genericClass;
    }

    public void check(HealthcareInstitutionModel institution, BigDecimal valueForCreateExam) {
        verifyIfZero(institution);
        verifyIfBalanceByReference(institution, valueForCreateExam);
    }

    private void verifyIfBalanceByReference(HealthcareInstitutionModel institution, BigDecimal valueForCreateExam) {
        if (institution.getCoin().doubleValue() < valueForCreateExam.doubleValue()) {
            throw genericClass;
        }
    }

    private void verifyIfZero(HealthcareInstitutionModel institution) {
        if (institution.getCoin().doubleValue() <= WITHOUT_BALANCE) {
            throw genericClass;
        }
    }
}
