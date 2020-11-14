package com.pixeon.healthcare.usecases.deleteexam;

import com.pixeon.healthcare.domain.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.exception.InstitutionNotFoundException;
import com.pixeon.healthcare.domain.models.HealthcareInstitution;
import com.pixeon.healthcare.domain.models.builders.ExamModelBuilder;
import com.pixeon.healthcare.domain.models.enums.Gender;
import com.pixeon.healthcare.usecases.createexam.ExamService;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.HealthcareInstitutionService;
import com.pixeon.healthcare.usecases.deleteexam.exception.ExamNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class DeleteExamTest {

    private static final int EXAM_ID = 1;
    private DeleteExam deleteExam;
    @Mock
    private ExamService examService;
    @Mock
    private HealthcareInstitutionService institutionService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.deleteExam = new DeleteExam(institutionService, examService);
        when(examService.delete(any())).thenReturn(true);
        when(institutionService.getCurrentInstitution()).thenReturn(new HealthcareInstitution.Builder()
                .name("Hospital Infantil")
                .cnpj("56.227.555/0001-25")
                .build());
        when(institutionService.getInstitutionForExamBy(anyInt())).thenReturn(new HealthcareInstitution.Builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build());
        when(examService.getExameById(anyInt())).thenReturn(ExamModelBuilder.Builder()
                .withId(1)
                .withPatientName("Wellton S. Barros")
                .withPatientAge(35)
                .withPatientGender(Gender.MALE)
                .withPhysicianName("Laiane Carvalho de Oliveira")
                .withPhysicianCRM(981651)
                .withProcedureName("Mentoplastia")
                .build());
    }

    @Test
    public void shouldDeleteExam() {
        when(institutionService.getInstitutionForExamBy(anyInt())).thenReturn(new HealthcareInstitution.Builder()
                .name("Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build());
        assertEquals(true, this.deleteExam.delete(EXAM_ID));
    }

    @Test
    public void shouldNotDeleteExamWhenExamNotFound() {
        when(examService.getExameById(anyInt())).thenReturn(null);

        ExamNotFoundException exception = assertThrows(ExamNotFoundException.class, () -> this.deleteExam.delete(EXAM_ID));
        assertEquals("Exame não foi encontrado!", exception.getMessage());
    }

    @Test
    public void shouldNotDeleteExamWhenExamDoesNotBelongInstitution() {
        InstitutionDoesNotOwnExamException exception = assertThrows(InstitutionDoesNotOwnExamException.class, () -> this.deleteExam.delete(EXAM_ID));
        assertEquals("Exame não pertence a atual instituição!", exception.getMessage());
    }

    @Test
    public void shouldNotDeleteExamWhenInstitutionNotFound() {
        when(institutionService.getCurrentInstitution()).thenReturn(null);
        when(institutionService.getInstitutionForExamBy(anyInt())).thenReturn(null);

        when(examService.getExameById(anyInt())).thenReturn(ExamModelBuilder.Builder()
                .withId(1)
                .withPatientName("Wellton S. Barros")
                .withPatientAge(35)
                .withPatientGender(Gender.MALE)
                .withPhysicianName("Laiane Carvalho de Oliveira")
                .withPhysicianCRM(981651)
                .withProcedureName("Mentoplastia")
                .build());

        InstitutionNotFoundException exception = assertThrows(InstitutionNotFoundException.class, () -> this.deleteExam.delete(EXAM_ID));
        assertEquals("Instituição não foi encontrada!", exception.getMessage());
    }

    private HealthcareInstitution createAnotherHealthcareInstitution() {
        return new HealthcareInstitution.Builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build();
    }
}
