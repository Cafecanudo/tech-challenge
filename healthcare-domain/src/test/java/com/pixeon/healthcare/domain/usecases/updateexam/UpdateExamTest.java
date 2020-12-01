package com.pixeon.healthcare.domain.usecases.updateexam;

import com.pixeon.healthcare.domain.config.enums.GenderEnum;
import com.pixeon.healthcare.domain.config.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.domain.config.exception.IdCantNullException;
import com.pixeon.healthcare.domain.config.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.config.exception.InstitutionNotFoundException;
import com.pixeon.healthcare.domain.entities.Exam;
import com.pixeon.healthcare.domain.entities.HealthcareInstitution;
import com.pixeon.healthcare.domain.usecases.createexam.ExamGateway;
import com.pixeon.healthcare.domain.usecases.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecases.updateexam.impl.UpdateExamImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class UpdateExamTest {

    private UpdateExam updateExam;
    @Mock
    private ExamGateway examGateway;
    @Mock
    private HealthcareInstitutionGateway institutionService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.updateExam = new UpdateExamImpl(institutionService, examGateway);
        when(examGateway.update(any())).thenAnswer(
                (Answer<Exam>) invocationOnMock -> invocationOnMock.getArgument(0));
        when(institutionService.getCurrentInstitution()).thenReturn(HealthcareInstitution.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build());
        when(institutionService.getInstitutionForExamBy(anyInt())).thenReturn(HealthcareInstitution.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build());
    }

    @Test
    public void shouldUpdateExam() {
        Exam exam = Exam.builder()
                .id(1)
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        this.updateExam.update(exam);
    }

    @Test
    public void shouldNotUpdateExamWhenIDIsNull() {
        Exam exam = Exam.builder()
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        IdCantNullException exception = assertThrows(IdCantNullException.class,
                () -> this.updateExam.update(exam));
        assertEquals("Id do exame não pode ser vazio!", exception.getMessage());
    }

    @Test
    public void shouldNotUpdateExamWhenExamModelIsInvalid() {
        Exam exam = Exam.builder().id(1).build();

        CreateExamFieldEmptyException exception = assertThrows(CreateExamFieldEmptyException.class,
                () -> this.updateExam.update(exam));
        assertEquals("Existem informações obrigatórios do exame que não foram preenchidas!", exception.getMessage());
    }

    @Test
    public void shouldNotUpdateExamWhenInstitutionNotFound() {
        Exam exam = Exam.builder()
                .id(1)
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        when(institutionService.getInstitutionForExamBy(anyInt())).thenReturn(null);

        InstitutionNotFoundException exception = assertThrows(InstitutionNotFoundException.class, () -> this.updateExam.update(exam));
        assertEquals("Instituição não foi encontrada!", exception.getMessage());
    }

    @Test
    public void shouldNotUpdateExamWhenExamDoesNotBelongInstitution() {
        Exam exam = Exam.builder()
                .id(1)
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        when(institutionService.getInstitutionForExamBy(exam.getId())).thenReturn(HealthcareInstitution.builder()
                .name("Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build());

        InstitutionDoesNotOwnExamException exception = assertThrows(InstitutionDoesNotOwnExamException.class, () -> this.updateExam.update(exam));
        assertEquals("Exame não pertence a atual instituição!", exception.getMessage());
    }

    @Test
    public void shouldUpdateExamWhenExamBelongsInstitution() {
        Exam exam = Exam.builder()
                .id(1)
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        this.updateExam.update(exam);
    }

}
