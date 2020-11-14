package com.pixeon.healthcare.usecases.createhealthcareInstitution;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidateCNPJTest {

    @Test
    public void shouldReturnTrueWhenValid() {
        assertTrue(ValidateCNPJ.isCNPJ("21.298.758/0001-28"));
    }

    @Test
    public void shouldReturnTrueWhenValidWithoutPoints() {
        assertTrue(ValidateCNPJ.isCNPJ("21298758000128"));
    }

    @Test
    public void shouldReturnFalseWhenInvalid() {
        assertFalse(ValidateCNPJ.isCNPJ("21.298.758/0001-99"));
    }

    @Test
    public void shouldReturnFalseWhenNumbersInSequence() {
        assertFalse(ValidateCNPJ.isCNPJ("99.999.999/9999-99"));
    }

    @Test
    public void shouldReturnFalseWhenMinNumbers() {
        assertFalse(ValidateCNPJ.isCNPJ("99.999.999"));
    }

    @Test
    public void shouldReturnFalseWhenMaxNumbers() {
        assertFalse(ValidateCNPJ.isCNPJ("99.999.999/9999-99999999999999"));
    }

    @Test
    public void shouldReturnFalseWhenCharacteres() {
        assertFalse(ValidateCNPJ.isCNPJ("AA"));
    }

    @Test
    public void shouldReturnFalseWhenEmptyValues() {
        assertFalse(ValidateCNPJ.isCNPJ(" "));
    }

}