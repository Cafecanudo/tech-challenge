package com.pixeon.healthcare.usecases.createexam.exception;

public class NoFoundHealthcareInstituitionException extends RuntimeException {

    public NoFoundHealthcareInstituitionException() {
        super("Não foi encontrada a instituição informada!");
    }
}
