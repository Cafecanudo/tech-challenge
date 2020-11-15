package com.pixeon.healthcare.usecases.createexam;

import com.pixeon.healthcare.domain.models.HealthcareInstitution;

import java.math.BigDecimal;

public class CheckBalanceInstitution<T extends RuntimeException> {

    private static final double WITHOUT_BALANCE = 0.0;
    private T t;

    public CheckBalanceInstitution(T t) {
        this.t = t;
    }

    public void check(HealthcareInstitution institution, BigDecimal valueForCreateExam) {
        verifyIfZero(institution);
        verifyIfBalanceByReference(institution, valueForCreateExam);
    }

    private void verifyIfBalanceByReference(HealthcareInstitution institution, BigDecimal valueForCreateExam) {
        if (institution.getCoins().doubleValue() < valueForCreateExam.doubleValue()) {
            throw t;
        }
    }

    private void verifyIfZero(HealthcareInstitution institution) {
        if (institution.getCoins().doubleValue() <= WITHOUT_BALANCE) {
            throw t;
        }
    }
}