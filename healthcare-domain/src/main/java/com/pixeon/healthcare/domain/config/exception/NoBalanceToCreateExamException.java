package com.pixeon.healthcare.domain.config.exception;

public class NoBalanceToCreateExamException extends RuntimeException {

    public NoBalanceToCreateExamException() {
        super("Instituição não possui saldo para criar exame!");
    }
}
