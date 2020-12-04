package com.pixeon.healthcare.domain.config.exception;

public class NegativeValuesNotAcceptedException extends RuntimeException {

    public NegativeValuesNotAcceptedException() {
        super("Valores negativos não são aceitos!");
    }
}
