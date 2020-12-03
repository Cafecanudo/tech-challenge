package com.pixeon.healthcare.domain.usecase.deleteexam;

import com.pixeon.healthcare.domain.config.exception.ExamNotFoundException;
import com.pixeon.healthcare.domain.config.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.config.exception.InstitutionNotFoundException;
import com.pixeon.healthcare.domain.model.ExamModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.model.enums.GenderEnum;
import com.pixeon.healthcare.domain.usecase.createexam.ExamGateway;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.deleteexam.impl.DeleteExamImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class DeleteExamModelTest {

    private static final int EXAM_ID = 1;
    private DeleteExam deleteExam;
    @Mock
    private ExamGateway examGateway;
    @Mock
    private HealthcareInstitutionGateway institutionService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.deleteExam = new DeleteExamImpl(institutionService, examGateway);
        when(examGateway.delete(any())).thenReturn(true);
        when(institutionService.getCurrentInstitution()).thenReturn(HealthcareInstitutionModel.builder()
                .name("Hospital Infantil")
                .cnpj("56.227.555/0001-25")
                .build());
        when(institutionService.getInstitutionForExamBy(anyInt())).thenReturn(HealthcareInstitutionModel.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build());
        when(examGateway.getExameById(anyInt())).thenReturn(ExamModel.builder()
                .id(1)
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build());
    }

    @Test
    public void shouldDeleteExam() {
        when(institutionService.getInstitutionForExamBy(anyInt())).thenReturn(HealthcareInstitutionModel.builder()
                .name("Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build());
        assertEquals(true, this.deleteExam.delete(EXAM_ID));
    }

    @Test
    public void shouldNotDeleteExamWhenExamNotFound() {
        when(examGateway.getExameById(anyInt())).thenReturn(null);

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

        when(examGateway.getExameById(anyInt())).thenReturn(ExamModel.builder()
                .id(1)
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build());

        InstitutionNotFoundException exception = assertThrows(InstitutionNotFoundException.class, () -> this.deleteExam.delete(EXAM_ID));
        assertEquals("Instituição não foi encontrada!", exception.getMessage());
    }
}
