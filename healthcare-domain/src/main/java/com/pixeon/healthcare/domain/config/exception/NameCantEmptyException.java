package com.pixeon.healthcare.domain.config.exception;

public class NameCantEmptyException extends RuntimeException {

    public NameCantEmptyException() {
        super("Nome da instituição está em branco.");
    }

}
