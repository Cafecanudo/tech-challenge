package com.pixeon.healthcare.domain.config.exception;

public class InstitutionNotFoundException extends RuntimeException {

    public InstitutionNotFoundException() {
        super("Instituição não foi encontrada!");
    }
}
