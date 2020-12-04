package com.pixeon.healthcare.domain.entity;

import com.pixeon.healthcare.domain.model.ExamModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.model.enums.GenderEnum;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class HealthcareInstitutionModelTest {

    @Test
    public void shouldReturnTrueWhereCNPJEquals() {
        HealthcareInstitutionModel institution1 = HealthcareInstitutionModel.builder()
                .id(1)
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build();

        HealthcareInstitutionModel institution2 = HealthcareInstitutionModel.builder()
                .id(2)
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build();

        assertTrue(institution1.equals(institution2));
    }

    @Test
    public void shouldReturnTrueWhereCNPJDiferents() {
        HealthcareInstitutionModel institution1 = HealthcareInstitutionModel.builder()
                .id(1)
                .name("Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build();

        HealthcareInstitutionModel institution2 = HealthcareInstitutionModel.builder()
                .id(1)
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build();

        assertFalse(institution1.equals(institution2));
    }

    @Test
    public void shouldReturnTrueWhenInstitutionIsOwnerExam() {
        HealthcareInstitutionModel institution1 = HealthcareInstitutionModel.builder()
                .id(1)
                .name("Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build();

        ExamModel examModel = ExamModel.builder()
                .id(1)
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .healthcareInstitution(HealthcareInstitutionModel.builder()
                        .name("Instituição de Saúde")
                        .cnpj("56.227.555/0001-25")
                        .build())
                .build();

        assertTrue(institution1.examOwner(examModel));
    }

    @Test
    public void shouldReturnExceptionWhenExamIsNullWhenCheckOwner() {
        HealthcareInstitutionModel institution1 = HealthcareInstitutionModel.builder()
                .id(1)
                .name("Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build();

        assertFalse(institution1.examOwner(null));
    }
}