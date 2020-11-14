package com.pixeon.healthcare.domain.exception;

public class InstitutionDoesNotOwnExamException extends RuntimeException {

    public InstitutionDoesNotOwnExamException() {
        super("Exame não pertence a atual instituição!");
    }
}
