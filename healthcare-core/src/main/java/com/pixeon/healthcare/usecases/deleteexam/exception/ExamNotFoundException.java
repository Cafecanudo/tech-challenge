package com.pixeon.healthcare.usecases.deleteexam.exception;

public class ExamNotFoundException extends RuntimeException {

    public ExamNotFoundException() {
        super("Exame não foi encontrado!");
    }
}
