package com.pixeon.healthcare.domain.config.exception;

public class CNPJInvalidException extends RuntimeException {

    public CNPJInvalidException() {
        super("CNPJ da instituição é inválido!");
    }
}
