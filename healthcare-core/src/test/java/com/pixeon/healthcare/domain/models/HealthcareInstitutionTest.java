package com.pixeon.healthcare.domain.models;

import com.pixeon.healthcare.domain.models.builders.ExamModelBuilder;
import com.pixeon.healthcare.domain.models.enums.Gender;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class HealthcareInstitutionTest {

    @Test
    public void shouldReturnTrueWhereCNPJEquals() {
        HealthcareInstitution institution1 = new HealthcareInstitution.Builder()
                .id(1)
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build();

        HealthcareInstitution institution2 = new HealthcareInstitution.Builder()
                .id(2)
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build();

        assertTrue(institution1.equals(institution2));
    }

    @Test
    public void shouldReturnTrueWhereCNPJDiferents() {
        HealthcareInstitution institution1 = new HealthcareInstitution.Builder()
                .id(1)
                .name("Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build();

        HealthcareInstitution institution2 = new HealthcareInstitution.Builder()
                .id(1)
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build();

        assertFalse(institution1.equals(institution2));
    }

    @Test
    public void shouldReturnTrueWhenInstitutionIsOwnerExam() {
        HealthcareInstitution institution1 = new HealthcareInstitution.Builder()
                .id(1)
                .name("Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build();

        ExamModel exam = ExamModelBuilder.Builder()
                .withId(1)
                .withPatientName("Wellton S. Barros")
                .withPatientAge(35)
                .withPatientGender(Gender.MALE)
                .withPhysicianName("Laiane Carvalho de Oliveira")
                .withPhysicianCRM(981651)
                .withProcedureName("Mentoplastia")
                .withHealthcareInstitution(new HealthcareInstitution.Builder()
                        .name("Instituição de Saúde")
                        .cnpj("56.227.555/0001-25")
                        .build())
                .build();

        assertTrue(institution1.examOwner(exam));
    }

    @Test
    public void shouldReturnExceptionWhenExamIsNullWhenCheckOwner() {
        HealthcareInstitution institution1 = new HealthcareInstitution.Builder()
                .id(1)
                .name("Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build();

        assertFalse(institution1.examOwner(null));
    }
}