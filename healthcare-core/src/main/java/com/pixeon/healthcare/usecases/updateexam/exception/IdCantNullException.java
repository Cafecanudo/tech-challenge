package com.pixeon.healthcare.usecases.updateexam.exception;

public class IdCantNullException extends RuntimeException {

    public IdCantNullException() {
        super("Id do exame não pode ser vazio!");
    }
}
