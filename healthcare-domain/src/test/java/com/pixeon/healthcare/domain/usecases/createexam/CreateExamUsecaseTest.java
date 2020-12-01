package com.pixeon.healthcare.domain.usecases.createexam;

import com.pixeon.healthcare.domain.config.enums.GenderEnum;
import com.pixeon.healthcare.domain.config.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.domain.config.exception.NoBalanceToCreateExamException;
import com.pixeon.healthcare.domain.entities.Exam;
import com.pixeon.healthcare.domain.entities.HealthcareInstitution;
import com.pixeon.healthcare.domain.usecases.createexam.impl.CreateExamUsecaseImpl;
import com.pixeon.healthcare.domain.usecases.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecases.getvalueconfigapplication.ApplicationConfigGateway;
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

public class CreateExamUsecaseTest {

    private static final BigDecimal ONE_COIN_FOR_CREATE_EXAM = new BigDecimal(1);
    private CreateExamUsecase createExamUsecase;
    @Mock
    private ExamGateway examGateway;
    @Mock
    private HealthcareInstitutionGateway institutionService;
    @Mock
    private ApplicationConfigGateway applicationConfigGateway;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.createExamUsecase = new CreateExamUsecaseImpl(examGateway, institutionService, applicationConfigGateway);
        when(applicationConfigGateway.getValueCreateExam()).thenReturn(ONE_COIN_FOR_CREATE_EXAM);
        when(institutionService.getCurrentInstitution()).thenReturn(HealthcareInstitution.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coin(new BigDecimal(18.58))
                .build());
    }

    @Test
    public void shouldCreateAExam() {
        when(examGateway.save(any(Exam.class))).then((Answer<Exam>) invocationOnMock -> {
            Exam exam = invocationOnMock.getArgument(0);
            exam.setId(1);
            return exam;
        });

        Exam exam = Exam.builder()
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        exam = this.createExamUsecase.create(exam);
        assertEquals(1, exam.getId(), 0.1);
        assertEquals(17.58, exam.getHealthcareInstitution().getCoin().doubleValue(), 0.1);
    }

    @Test
    public void shouldNotCreateExamWhenExamModelIsInvalid() {
        Exam exam = Exam.builder().build();

        CreateExamFieldEmptyException exception = assertThrows(CreateExamFieldEmptyException.class,
                () -> this.createExamUsecase.create(exam));
        assertEquals("Existem informações obrigatórios do exame que não foram preenchidas!", exception.getMessage());
    }

    @Test
    public void shouldNotCreateExamWhenHealthcareInstitutionHasNoBalance() {
        when(institutionService.getCurrentInstitution()).thenReturn(HealthcareInstitution.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coin(new BigDecimal(0.0))
                .build());

        Exam exam = Exam.builder()
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        NoBalanceToCreateExamException exception = assertThrows(NoBalanceToCreateExamException.class,
                () -> this.createExamUsecase.create(exam));
        assertEquals("Instituição não possui saldo para criar exame!", exception.getMessage());
    }

    @Test
    public void shouldNotCreateExamWhenHealthInstitutionDoesNotHaveEnoughBalance() {
        when(institutionService.getCurrentInstitution()).thenReturn(HealthcareInstitution.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coin(new BigDecimal(0.7))
                .build());

        Exam exam = Exam.builder()
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        NoBalanceToCreateExamException exception = assertThrows(NoBalanceToCreateExamException.class,
                () -> this.createExamUsecase.create(exam));
        assertEquals("Instituição não possui saldo para criar exame!", exception.getMessage());
    }

    @Test
    public void verifyWhenCreateExamItsCallingUpdateInstitutionNewValueAfterCreateExam() {
        Exam exam = Exam.builder()
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        this.createExamUsecase.create(exam);
        verify(institutionService, times(1)).update(any());
    }
}
