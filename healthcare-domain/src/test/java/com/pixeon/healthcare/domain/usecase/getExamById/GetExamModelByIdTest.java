package com.pixeon.healthcare.domain.usecase.getExamById;

import com.pixeon.healthcare.domain.config.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.config.exception.NoBalanceToConsultingExamException;
import com.pixeon.healthcare.domain.model.ExamModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.model.enums.GenderEnum;
import com.pixeon.healthcare.domain.usecase.createexam.ExamGateway;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.getExamById.impl.GetExamByIdImpl;
import com.pixeon.healthcare.domain.usecase.getvalueconfigapplication.ApplicationConfigGateway;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class GetExamModelByIdTest {

    private static final int EXAM_ID = 1;
    private static final BigDecimal VALUE_FOR_CONSULTING_EXAM = new BigDecimal(4.78);
    private GetExamById getExamById;
    @Mock
    private ExamGateway examGateway;
    @Mock
    private ApplicationConfigGateway applicationConfigGateway;
    @Mock
    private HealthcareInstitutionGateway healthcareInstitutionGateway;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.getExamById = new GetExamByIdImpl(applicationConfigGateway, examGateway, healthcareInstitutionGateway);
        when(examGateway.getExameById(anyInt())).thenReturn(createExam());
        when(applicationConfigGateway.getValueForConsultingExam()).thenReturn(VALUE_FOR_CONSULTING_EXAM);
        when(healthcareInstitutionGateway.getCurrentInstitution()).thenReturn(HealthcareInstitutionModel.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coin(new BigDecimal(20.0))
                .build());
    }

    @Test
    public void shouldReturnExam() {
        ExamModel examModel = this.getExamById.get(EXAM_ID);

        assertEquals(1, examModel.getId(), 0.1);
        assertEquals("Wellton S. Barros", examModel.getPatientName());
        assertEquals(35, examModel.getPatientAge(), 0.1);
        assertEquals(GenderEnum.MALE, examModel.getPatientGender());
        assertEquals("Laiane Carvalho de Oliveira", examModel.getPhysicianName());
        assertEquals(981651, examModel.getPhysicianCRM(), 0.1);
        assertEquals("Mentoplastia", examModel.getProcedureName());
    }

    @Test
    public void shouldNotReturnExamFromAnotherInstitution() {
        when(healthcareInstitutionGateway.getCurrentInstitution()).thenReturn(HealthcareInstitutionModel.builder()
                .name("Outra Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build());

        InstitutionDoesNotOwnExamException exception = assertThrows(InstitutionDoesNotOwnExamException.class, () -> this.getExamById.get(EXAM_ID));
        assertEquals("Exame não pertence a atual instituição!", exception.getMessage());
    }

    @Test
    public void shouldReturnExceptionWhenInstitutionNotHaveEnoughBalance() {
        when(healthcareInstitutionGateway.getCurrentInstitution()).thenReturn(HealthcareInstitutionModel.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coin(new BigDecimal(1.2))
                .build());

        NoBalanceToConsultingExamException exception = assertThrows(NoBalanceToConsultingExamException.class, () -> this.getExamById.get(EXAM_ID));
        assertEquals("Instituição não possui saldo para consultar exames!", exception.getMessage());
    }

    @Test
    public void shouldReturnNewBalanceAfterConsultationCharge() {
        when(healthcareInstitutionGateway.getCurrentInstitution()).thenReturn(HealthcareInstitutionModel.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coin(new BigDecimal(15.78))
                .build());

        ExamModel exam = createExam();
        when(examGateway.getExameById(anyInt())).thenReturn(exam);

        ExamModel examModel = this.getExamById.get(EXAM_ID);
        assertEquals(11.00, examModel.getHealthcareInstitution().getCoin().doubleValue(), 0.1);
    }

    @Test
    public void shouldChargeOnlyOnceWhenExamIsRecovered() {
        when(healthcareInstitutionGateway.getCurrentInstitution()).thenReturn(HealthcareInstitutionModel.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coin(new BigDecimal(15.78))
                .build());

        ExamModel exam = createExam();
        exam.setBilled(true);
        when(examGateway.getExameById(anyInt())).thenReturn(exam);

        ExamModel examModel = this.getExamById.get(EXAM_ID);
        assertEquals(15.78, examModel.getHealthcareInstitution().getCoin().doubleValue(), 0.1);
    }

    @Test
    public void verifyIfUpdateIsCallingWhenConsultingExam() {
        this.getExamById.get(EXAM_ID);
        verify(healthcareInstitutionGateway, times(1)).updateBalance(any(), any());
    }

    private ExamModel createExam() {
        return ExamModel.builder()
                .id(1)
                .patientName("Wellton S. Barros")
                .patientAge(35)
                .patientGender(GenderEnum.MALE)
                .physicianName("Laiane Carvalho de Oliveira")
                .physicianCRM(981651)
                .procedureName("Mentoplastia")
                .healthcareInstitution(createInstitution())
                .build();
    }

    private HealthcareInstitutionModel createInstitution() {
        return HealthcareInstitutionModel.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coin(new BigDecimal(20.0))
                .build();
    }
}