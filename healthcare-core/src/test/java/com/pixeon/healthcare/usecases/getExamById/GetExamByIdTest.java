package com.pixeon.healthcare.usecases.getExamById;

import com.pixeon.healthcare.domain.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.models.ExamModel;
import com.pixeon.healthcare.domain.models.HealthcareInstitutionDTO;
import com.pixeon.healthcare.domain.models.builders.ExamModelBuilder;
import com.pixeon.healthcare.domain.models.enums.Gender;
import com.pixeon.healthcare.usecases.createexam.ExamService;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.HealthcareInstitutionFactory;
import com.pixeon.healthcare.usecases.getExamById.exception.NoBalanceToConsultingExamException;
import com.pixeon.healthcare.usecases.getvalueconfigapplication.ApplicationConfigFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class GetExamByIdTest {

    private static final int EXAM_ID = 1;
    private static final BigDecimal VALUE_FOR_CONSULTING_EXAM = new BigDecimal(4.78);
    private GetExamById getExamById;
    @Mock
    private ExamService examService;
    @Mock
    private ApplicationConfigFactory applicationConfigFactory;
    @Mock
    private HealthcareInstitutionFactory healthcareInstitutionFactory;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.getExamById = new GetExamById(applicationConfigFactory, examService, healthcareInstitutionFactory);
        when(examService.getExameById(anyInt())).thenReturn(createExam());
        when(applicationConfigFactory.getValueForConsultingExam()).thenReturn(VALUE_FOR_CONSULTING_EXAM);
        when(healthcareInstitutionFactory.getCurrentInstitution()).thenReturn(new HealthcareInstitutionDTO.Builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coins(new BigDecimal(20.0))
                .build());
    }

    @Test
    public void shouldReturnExam() {
        ExamModel examModel = this.getExamById.get(EXAM_ID);

        assertEquals(1, examModel.getId(), 0.1);
        assertEquals("Wellton S. Barros", examModel.getPatientName());
        assertEquals(35, examModel.getPatientAge(), 0.1);
        assertEquals(Gender.MALE, examModel.getPatientGender());
        assertEquals("Laiane Carvalho de Oliveira", examModel.getPhysicianName());
        assertEquals(981651, examModel.getPhysicianCRM(), 0.1);
        assertEquals("Mentoplastia", examModel.getProcedureName());
    }

    @Test
    public void shouldNotReturnExamFromAnotherInstitution() {
        when(healthcareInstitutionFactory.getCurrentInstitution()).thenReturn(new HealthcareInstitutionDTO.Builder()
                .name("Outra Instituição de Saúde")
                .cnpj("56.227.555/0001-25")
                .build());

        InstitutionDoesNotOwnExamException exception = assertThrows(InstitutionDoesNotOwnExamException.class, () -> this.getExamById.get(EXAM_ID));
        assertEquals("Exame não pertence a atual instituição!", exception.getMessage());
    }

    @Test
    public void shouldReturnExceptionWhenInstitutionNotHaveEnoughBalance() {
        when(healthcareInstitutionFactory.getCurrentInstitution()).thenReturn(new HealthcareInstitutionDTO.Builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coins(new BigDecimal(1.2))
                .build());

        NoBalanceToConsultingExamException exception = assertThrows(NoBalanceToConsultingExamException.class, () -> this.getExamById.get(EXAM_ID));
        assertEquals("Instituição não possui saldo para consultar exames!", exception.getMessage());
    }

    @Test
    public void shouldReturnNewBalanceAfterConsultationCharge() {
        when(healthcareInstitutionFactory.getCurrentInstitution()).thenReturn(new HealthcareInstitutionDTO.Builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coins(new BigDecimal(15.78))
                .build());

        ExamModel exam = createExam();
        when(examService.getExameById(anyInt())).thenReturn(exam);

        ExamModel examModel = this.getExamById.get(EXAM_ID);
        assertEquals(11.00, examModel.getHealthcareInstitution().getCoins().doubleValue(), 0.1);
    }

    @Test
    public void shouldChargeOnlyOnceWhenExamIsRecovered() {
        when(healthcareInstitutionFactory.getCurrentInstitution()).thenReturn(new HealthcareInstitutionDTO.Builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coins(new BigDecimal(15.78))
                .build());

        ExamModel exam = createExam();
        exam.setBilled(true);
        when(examService.getExameById(anyInt())).thenReturn(exam);

        ExamModel examModel = this.getExamById.get(EXAM_ID);
        assertEquals(15.78, examModel.getHealthcareInstitution().getCoins().doubleValue(), 0.1);
    }

    @Test
    public void verifyIfUpdateIsCallingWhenConsultingExam() {
        this.getExamById.get(EXAM_ID);
        verify(healthcareInstitutionFactory, times(1)).update(any());
    }

    private ExamModel createExam() {
        return ExamModelBuilder.Builder()
                .withId(1)
                .withPatientName("Wellton S. Barros")
                .withPatientAge(35)
                .withPatientGender(Gender.MALE)
                .withPhysicianName("Laiane Carvalho de Oliveira")
                .withPhysicianCRM(981651)
                .withProcedureName("Mentoplastia")
                .withHealthcareInstitution(createInstitution())
                .build();
    }

    private HealthcareInstitutionDTO createInstitution() {
        return new HealthcareInstitutionDTO.Builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .coins(new BigDecimal(20.0))
                .build();
    }
}