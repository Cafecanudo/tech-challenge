package com.pixeon.healthcare.domain.config.exception;

public class JaExisteCNPJException extends RuntimeException {

    public JaExisteCNPJException() {
        super("Já existe CNPJ cadastrado!");
    }
}
