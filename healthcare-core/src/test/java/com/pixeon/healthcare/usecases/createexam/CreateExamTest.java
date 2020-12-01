package com.pixeon.healthcare.usecases.createexam;

import com.pixeon.healthcare.domain.models.ExamModel;
import com.pixeon.healthcare.domain.models.HealthcareInstitutionDTO;
import com.pixeon.healthcare.domain.models.builders.ExamModelBuilder;
import com.pixeon.healthcare.domain.models.enums.Gender;
import com.pixeon.healthcare.usecases.createexam.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.usecases.createexam.exception.NoBalanceToCreateExamException;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.HealthcareInstitutionFactory;
import com.pixeon.healthcare.usecases.getvalueconfigapplication.ApplicationConfigFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateExamTest {

    private static final BigDecimal ONE_COIN_FOR_CREATE_EXAM = new BigDecimal(1);
    private CreateExam createExam;
    @Mock
    private ExamService examService;
    @Mock
    private HealthcareInstitutionFactory institutionService;
    @Mock
    private ApplicationConfigFactory applicationConfigFactory;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.createExam = new CreateExam(examService, institutionService, applicationConfigFactory);
        when(applicationConfigFactory.getValueCreateExam()).thenReturn(ONE_COIN_FOR_CREATE_EXAM);
        when(institutionService.getCurrentInstitution()).thenReturn(new HealthcareInstitutionDTO.Builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coins(new BigDecimal(18.58))
                .build());
    }

    @Test
    public void shouldCreateAExam() {
        when(examService.save(any(ExamModel.class))).then((Answer<ExamModel>) invocationOnMock -> {
            ExamModel exam = invocationOnMock.getArgument(0);
            exam.setId(1);
            return exam;
        });

        ExamModel examModel = ExamModelBuilder.Builder()
                .withPatientName("Wellton S. Barros")
                .withPatientAge(35)
                .withPatientGender(Gender.MALE)
                .withPhysicianName("Laiane Carvalho de Oliveira")
                .withPhysicianCRM(981651)
                .withProcedureName("Mentoplastia")
                .build();

        examModel = this.createExam.create(examModel);
        assertEquals(1, examModel.getId(), 0.1);
        assertEquals(17.58, examModel.getHealthcareInstitution().getCoins().doubleValue(), 0.1);
    }

    @Test
    public void shouldNotCreateExamWhenExamModelIsInvalid() {
        ExamModel examModel = ExamModelBuilder.Builder().build();

        CreateExamFieldEmptyException exception = assertThrows(CreateExamFieldEmptyException.class,
                () -> this.createExam.create(examModel));
        assertEquals("Existem informações obrigatórios do exame que não foram preenchidas!", exception.getMessage());
    }

    @Test
    public void shouldNotCreateExamWhenHealthcareInstitutionHasNoBalance() {
        when(institutionService.getCurrentInstitution()).thenReturn(new HealthcareInstitutionDTO.Builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coins(new BigDecimal(0.0))
                .build());

        ExamModel examModel = ExamModelBuilder.Builder()
                .withPatientName("Wellton S. Barros")
                .withPatientAge(35)
                .withPatientGender(Gender.MALE)
                .withPhysicianName("Laiane Carvalho de Oliveira")
                .withPhysicianCRM(981651)
                .withProcedureName("Mentoplastia")
                .build();

        NoBalanceToCreateExamException exception = assertThrows(NoBalanceToCreateExamException.class,
                () -> this.createExam.create(examModel));
        assertEquals("Instituição não possui saldo para criar exame!", exception.getMessage());
    }

    @Test
    public void shouldNotCreateExamWhenHealthInstitutionDoesNotHaveEnoughBalance() {
        when(institutionService.getCurrentInstitution()).thenReturn(new HealthcareInstitutionDTO.Builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coins(new BigDecimal(0.7))
                .build());

        ExamModel examModel = ExamModelBuilder.Builder()
                .withPatientName("Wellton S. Barros")
                .withPatientAge(35)
                .withPatientGender(Gender.MALE)
                .withPhysicianName("Laiane Carvalho de Oliveira")
                .withPhysicianCRM(981651)
                .withProcedureName("Mentoplastia")
                .build();

        NoBalanceToCreateExamException exception = assertThrows(NoBalanceToCreateExamException.class,
                () -> this.createExam.create(examModel));
        assertEquals("Instituição não possui saldo para criar exame!", exception.getMessage());
    }

    @Test
    public void verifyWhenCreateExamItsCallingUpdateInstitutionWithNewValueAfterCreateExam() {
        ExamModel examModel = ExamModelBuilder.Builder()
                .withPatientName("Wellton S. Barros")
                .withPatientAge(35)
                .withPatientGender(Gender.MALE)
                .withPhysicianName("Laiane Carvalho de Oliveira")
                .withPhysicianCRM(981651)
                .withProcedureName("Mentoplastia")
                .build();

        this.createExam.create(examModel);
        verify(institutionService, times(1)).update(any());
    }
}
