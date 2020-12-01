package com.pixeon.healthcare.domain.config.exception;

public class CNPJJaExisteException extends RuntimeException {

    public CNPJJaExisteException() {
        super("Já existe CNPJ cadastrado!");
    }
}
