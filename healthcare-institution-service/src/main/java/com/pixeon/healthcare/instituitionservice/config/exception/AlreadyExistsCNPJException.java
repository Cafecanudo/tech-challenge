package com.pixeon.healthcare.instituitionservice.config.exception;

public class AlreadyExistsCNPJException extends RuntimeException {

    public AlreadyExistsCNPJException() {
        super("Já existe CNPJ cadastrado!");
    }
}
