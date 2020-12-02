package com.pixeon.healthcare.domain.config.exception;

public class NoBalanceToConsultingExamException extends RuntimeException {

    public NoBalanceToConsultingExamException() {
        super("Instituição não possui saldo para consultar exames!");
    }
}
