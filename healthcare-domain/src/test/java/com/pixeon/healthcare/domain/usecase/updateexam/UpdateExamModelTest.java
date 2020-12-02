package com.pixeon.healthcare.domain.usecase.updateexam;

import com.pixeon.healthcare.domain.config.enums.GenderEnum;
import com.pixeon.healthcare.domain.config.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.domain.config.exception.IdCantNullException;
import com.pixeon.healthcare.domain.config.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.config.exception.InstitutionNotFoundException;
import com.pixeon.healthcare.domain.model.ExamModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.usecase.createexam.ExamGateway;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.updateexam.impl.UpdateExamImpl;
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

public class UpdateExamModelTest {

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
                (Answer<ExamModel>) invocationOnMock -> invocationOnMock.getArgument(0));
        when(institutionService.getCurrentInstitution()).thenReturn(HealthcareInstitutionModel.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build());
        when(institutionService.getInstitutionForExamBy(anyInt())).thenReturn(HealthcareInstitutionModel.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build());
    }

    @Test
    public void shouldUpdateExam() {
        ExamModel examModel = ExamModel.builder()
                .id(1)
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        this.updateExam.update(examModel);
    }

    @Test
    public void shouldNotUpdateExamWhenIDIsNull() {
        ExamModel examModel = ExamModel.builder()
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        IdCantNullException exception = assertThrows(IdCantNullException.class,
                () -> this.updateExam.update(examModel));
        assertEquals("Id do exame não pode ser vazio!", exception.getMessage());
    }

    @Test
    public void shouldNotUpdateExamWhenExamModelIsInvalid() {
        ExamModel examModel = ExamModel.builder().id(1).build();

        CreateExamFieldEmptyException exception = assertThrows(CreateExamFieldEmptyException.class,
                () -> this.updateExam.update(examModel));
        assertEquals("Existem informações obrigatórios do exame que não foram preenchidas!", exception.getMessage());
    }

    @Test
    public void shouldNotUpdateExamWhenInstitutionNotFound() {
        ExamModel examModel = ExamModel.builder()
                .id(1)
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        when(institutionService.getInstitutionForExamBy(anyInt())).thenReturn(null);

        InstitutionNotFoundException exception = assertThrows(InstitutionNotFoundException.class, () -> this.updateExam.update(examModel));
        assertEquals("Instituição não foi encontrada!", exception.getMessage());
    }

    @Test
    public void shouldNotUpdateExamWhenExamDoesNotBelongInstitution() {
        ExamModel examModel = ExamModel.builder()
                .id(1)
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        when(institutionService.getInstitutionForExamBy(examModel.getId())).thenReturn(HealthcareInstitutionModel.builder()
                .name("Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build());

        InstitutionDoesNotOwnExamException exception = assertThrows(InstitutionDoesNotOwnExamException.class, () -> this.updateExam.update(examModel));
        assertEquals("Exame não pertence a atual instituição!", exception.getMessage());
    }

    @Test
    public void shouldUpdateExamWhenExamBelongsInstitution() {
        ExamModel examModel = ExamModel.builder()
                .id(1)
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        this.updateExam.update(examModel);
    }

}
