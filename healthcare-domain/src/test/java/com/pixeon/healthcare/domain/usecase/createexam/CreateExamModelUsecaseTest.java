package com.pixeon.healthcare.domain.usecase.createexam;

import com.pixeon.healthcare.domain.config.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.domain.config.exception.NoBalanceToCreateExamException;
import com.pixeon.healthcare.domain.model.ExamModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.model.enums.GenderEnum;
import com.pixeon.healthcare.domain.usecase.createexam.impl.CreateExamUsecaseImpl;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.getvalueconfigapplication.ApplicationConfigGateway;
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

public class CreateExamModelUsecaseTest {

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
        when(institutionService.getCurrentInstitution()).thenReturn(HealthcareInstitutionModel.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coin(new BigDecimal(18.58))
                .build());
    }

    @Test
    public void shouldCreateAExam() {
        when(examGateway.save(any(ExamModel.class))).then((Answer<ExamModel>) invocationOnMock -> {
            ExamModel examModel = invocationOnMock.getArgument(0);
            examModel.setId(1);
            return examModel;
        });

        ExamModel examModel = ExamModel.builder()
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        examModel = this.createExamUsecase.create(examModel);
        assertEquals(1, examModel.getId(), 0.1);
        assertEquals(17.58, examModel.getHealthcareInstitution().getCoin().doubleValue(), 0.1);
    }

    @Test
    public void shouldNotCreateExamWhenExamModelIsInvalid() {
        ExamModel examModel = ExamModel.builder().build();

        CreateExamFieldEmptyException exception = assertThrows(CreateExamFieldEmptyException.class,
                () -> this.createExamUsecase.create(examModel));
        assertEquals("Existem informações obrigatórios do exame que não foram preenchidas!", exception.getMessage());
    }

    @Test
    public void shouldNotCreateExamWhenHealthcareInstitutionHasNoBalance() {
        when(institutionService.getCurrentInstitution()).thenReturn(HealthcareInstitutionModel.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coin(new BigDecimal(0.0))
                .build());

        ExamModel examModel = ExamModel.builder()
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        NoBalanceToCreateExamException exception = assertThrows(NoBalanceToCreateExamException.class,
                () -> this.createExamUsecase.create(examModel));
        assertEquals("Instituição não possui saldo para criar exame!", exception.getMessage());
    }

    @Test
    public void shouldNotCreateExamWhenHealthInstitutionDoesNotHaveEnoughBalance() {
        when(institutionService.getCurrentInstitution()).thenReturn(HealthcareInstitutionModel.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coin(new BigDecimal(0.7))
                .build());

        ExamModel examModel = ExamModel.builder()
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        NoBalanceToCreateExamException exception = assertThrows(NoBalanceToCreateExamException.class,
                () -> this.createExamUsecase.create(examModel));
        assertEquals("Instituição não possui saldo para criar exame!", exception.getMessage());
    }

    @Test
    public void verifyWhenCreateExamItsCallingUpdateInstitutionNewValueAfterCreateExam() {
        ExamModel examModel = ExamModel.builder()
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .build();

        this.createExamUsecase.create(examModel);
        verify(institutionService, times(1)).updateBalance(any(), any());
    }
}
