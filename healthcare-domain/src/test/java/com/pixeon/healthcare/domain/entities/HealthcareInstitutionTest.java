package com.pixeon.healthcare.domain.entities;

import com.pixeon.healthcare.domain.config.enums.GenderEnum;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class HealthcareInstitutionTest {

    @Test
    public void shouldReturnTrueWhereCNPJEquals() {
        HealthcareInstitution institution1 = HealthcareInstitution.builder()
                .id(1)
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build();

        HealthcareInstitution institution2 = HealthcareInstitution.builder()
                .id(2)
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build();

        assertTrue(institution1.equals(institution2));
    }

    @Test
    public void shouldReturnTrueWhereCNPJDiferents() {
        HealthcareInstitution institution1 = HealthcareInstitution.builder()
                .id(1)
                .name("Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build();

        HealthcareInstitution institution2 = HealthcareInstitution.builder()
                .id(1)
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build();

        assertFalse(institution1.equals(institution2));
    }

    @Test
    public void shouldReturnTrueWhenInstitutionIsOwnerExam() {
        HealthcareInstitution institution1 = HealthcareInstitution.builder()
                .id(1)
                .name("Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build();

        Exam exam = Exam.builder()
                .id(1)
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .healthcareInstitution(HealthcareInstitution.builder()
                        .name("Instituição de Saúde")
                        .cnpj("56.227.555/0001-25")
                        .build())
                .build();

        assertTrue(institution1.examOwner(exam));
    }

    @Test
    public void shouldReturnExceptionWhenExamIsNullWhenCheckOwner() {
        HealthcareInstitution institution1 = HealthcareInstitution.builder()
                .id(1)
                .name("Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build();

        assertFalse(institution1.examOwner(null));
    }
}