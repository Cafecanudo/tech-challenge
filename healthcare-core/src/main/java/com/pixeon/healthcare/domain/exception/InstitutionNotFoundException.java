package com.pixeon.healthcare.domain.exception;

public class InstitutionNotFoundException extends RuntimeException {

    public InstitutionNotFoundException() {
        super("Instituição não foi encontrada!");
    }
}
