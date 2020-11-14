package com.pixeon.healthcare.usecases.updateexam;

import com.pixeon.healthcare.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.exception.InstitutionNotFoundException;
import com.pixeon.healthcare.models.ExamModel;
import com.pixeon.healthcare.models.HealthcareInstitution;
import com.pixeon.healthcare.models.builders.ExamModelBuilder;
import com.pixeon.healthcare.models.enums.Gender;
import com.pixeon.healthcare.usecases.createexam.ExamService;
import com.pixeon.healthcare.usecases.createexam.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.HealthcareInstitutionService;
import com.pixeon.healthcare.usecases.updateexam.exception.IdCantNullException;
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
    private ExamService examService;
    @Mock
    private HealthcareInstitutionService institutionService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.updateExam = new UpdateExam(institutionService, examService);
        when(examService.update(any())).thenAnswer(
                (Answer<ExamModel>) invocationOnMock -> invocationOnMock.getArgument(0));
        when(institutionService.getCurrentInstitution()).thenReturn(new HealthcareInstitution.Builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build());
        when(institutionService.getInstitutionForExamBy(anyInt())).thenReturn(new HealthcareInstitution.Builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build());
    }

    @Test
    public void shouldUpdateExam() {
        ExamModel examModel = ExamModelBuilder.Builder()
                .withId(1)
                .withPatientName("Wellton S. Barros")
                .withPatientAge(35)
                .withPatientGender(Gender.MALE)
                .withPhysicianName("Laiane Carvalho de Oliveira")
                .withPhysicianCRM(981651)
                .withProcedureName("Mentoplastia")
                .build();

        this.updateExam.update(examModel);
    }

    @Test
    public void shouldNotUpdateExamWhenIDIsNull() {
        ExamModel examModel = ExamModelBuilder.Builder()
                .withPatientName("Wellton S. Barros")
                .withPatientAge(35)
                .withPatientGender(Gender.MALE)
                .withPhysicianName("Laiane Carvalho de Oliveira")
                .withPhysicianCRM(981651)
                .withProcedureName("Mentoplastia")
                .build();

        IdCantNullException exception = assertThrows(IdCantNullException.class,
                () -> this.updateExam.update(examModel));
        assertEquals("Id do exame não pode ser vazio!", exception.getMessage());
    }

    @Test
    public void shouldNotUpdateExamWhenExamModelIsInvalid() {
        ExamModel examModel = ExamModelBuilder.Builder().withId(1).build();

        CreateExamFieldEmptyException exception = assertThrows(CreateExamFieldEmptyException.class,
                () -> this.updateExam.update(examModel));
        assertEquals("Existem informações obrigatórios do exame que não foram preenchidas!", exception.getMessage());
    }

    @Test
    public void shouldNotUpdateExamWhenInstitutionNotFound() {
        ExamModel examModel = ExamModelBuilder.Builder()
                .withId(1)
                .withPatientName("Wellton S. Barros")
                .withPatientAge(35)
                .withPatientGender(Gender.MALE)
                .withPhysicianName("Laiane Carvalho de Oliveira")
                .withPhysicianCRM(981651)
                .withProcedureName("Mentoplastia")
                .build();

        when(institutionService.getInstitutionForExamBy(anyInt())).thenReturn(null);

        InstitutionNotFoundException exception = assertThrows(InstitutionNotFoundException.class, () -> this.updateExam.update(examModel));
        assertEquals("Instituição não foi encontrada!", exception.getMessage());
    }

    @Test
    public void shouldNotUpdateExamWhenExamDoesNotBelongInstitution() {
        ExamModel examModel = ExamModelBuilder.Builder()
                .withId(1)
                .withPatientName("Wellton S. Barros")
                .withPatientAge(35)
                .withPatientGender(Gender.MALE)
                .withPhysicianName("Laiane Carvalho de Oliveira")
                .withPhysicianCRM(981651)
                .withProcedureName("Mentoplastia")
                .build();

        when(institutionService.getInstitutionForExamBy(examModel.getId())).thenReturn(new HealthcareInstitution.Builder()
                .name("Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build());

        InstitutionDoesNotOwnExamException exception = assertThrows(InstitutionDoesNotOwnExamException.class, () -> this.updateExam.update(examModel));
        assertEquals("Exame não pertence a atual instituição!", exception.getMessage());
    }

    @Test
    public void shouldUpdateExamWhenExamBelongsInstitution() {
        ExamModel examModel = ExamModelBuilder.Builder()
                .withId(1)
                .withPatientName("Wellton S. Barros")
                .withPatientAge(35)
                .withPatientGender(Gender.MALE)
                .withPhysicianName("Laiane Carvalho de Oliveira")
                .withPhysicianCRM(981651)
                .withProcedureName("Mentoplastia")
                .build();

        this.updateExam.update(examModel);
    }

}
