package com.pixeon.healthcare.usecases.updateexam;

import com.pixeon.healthcare.models.ExamModel;
import com.pixeon.healthcare.models.builders.ExamModelBuilder;
import com.pixeon.healthcare.models.enums.Gender;
import com.pixeon.healthcare.usecases.createexam.ExamService;
import com.pixeon.healthcare.usecases.createexam.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.usecases.updateexam.exception.IdCantNullException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class UpdateExamTest {

    private UpdateExam updateExam;
    @Mock
    private ExamService examService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.updateExam = new UpdateExam(examService);
        Mockito.when(examService.update(Mockito.any())).thenAnswer(
                (Answer<ExamModel>) invocationOnMock -> invocationOnMock.getArgument(0));
    }

    @Test
    public void shouldUpdateExame() {
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
    public void shouldNotUpdateExameWhenIDIsNull(){
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
    public void shouldNotUpdateExamWhenExamModelIsInvalid(){
        ExamModel examModel = ExamModelBuilder.Builder().withId(1).build();

        CreateExamFieldEmptyException exception = assertThrows(CreateExamFieldEmptyException.class,
                () -> this.updateExam.update(examModel));
        assertEquals("Existem informações obrigatórios do exame que não foram preenchidas!", exception.getMessage());
    }

}
