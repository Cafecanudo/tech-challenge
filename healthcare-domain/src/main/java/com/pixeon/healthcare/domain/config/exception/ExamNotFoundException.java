package com.pixeon.healthcare.domain.config.exception;

public class ExamNotFoundException extends RuntimeException {

    public ExamNotFoundException() {
        super("Exame não foi encontrado!");
    }
}
