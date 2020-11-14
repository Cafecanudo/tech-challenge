package com.pixeon.healthcare.exception;

public class InstitutionNotFoundException extends RuntimeException {

    public InstitutionNotFoundException() {
        super("Instituição não foi encontrada!");
    }
}
