package com.pixeon.healthcare.exception;

public class InstitutionDoesNotOwnExamException extends RuntimeException {

    public InstitutionDoesNotOwnExamException() {
        super("Exame não pertence a atual instituição!");
    }
}
