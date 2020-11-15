package com.pixeon.healthcare.usecases.getExamById.exception;

public class NoBalanceToConsultingExamException extends RuntimeException {

    public NoBalanceToConsultingExamException() {
        super("Instituição não possui saldo para consultar exames!");
    }
}
