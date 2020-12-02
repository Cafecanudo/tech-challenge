package com.pixeon.healthcare.domain.config.exception;

public class IdCantNullException extends RuntimeException {

    public IdCantNullException() {
        super("Id do exame não pode ser vazio!");
    }
}
