package com.pixeon.healthcare.domain.config.exception;

public class CreateExamFieldEmptyException extends RuntimeException {

    public CreateExamFieldEmptyException() {
        super("Existem informações obrigatórios do exame que não foram preenchidas!");
    }
}