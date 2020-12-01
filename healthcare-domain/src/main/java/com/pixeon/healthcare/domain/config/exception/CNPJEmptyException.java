package com.pixeon.healthcare.domain.config.exception;

public class CNPJEmptyException extends RuntimeException {

    public CNPJEmptyException() {
        super("CNPJ da instituição está em branco!");
    }
}
