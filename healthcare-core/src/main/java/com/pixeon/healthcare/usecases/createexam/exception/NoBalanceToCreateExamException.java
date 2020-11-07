package com.pixeon.healthcare.usecases.createexam.exception;

public class NoBalanceToCreateExamException extends RuntimeException {

    public NoBalanceToCreateExamException() {
        super("Instituição não possui saldo para criar exame!");
    }
}
