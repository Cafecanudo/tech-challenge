package com.pixeon.healthcare.examservice.entrypoint.rest.createexam;

import com.pixeon.healthcare.domain.config.exception.NegativeValuesNotAcceptedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(CreateExamController.class)
public class CreateExamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateExamPresenter createExamPresenter;

    @Test
    public void shouldCreateExam() throws Exception {
        Mockito.when(createExamPresenter.create(Mockito.any())).thenReturn(ExamDTO.builder()
                .id(1)
                .healthcareInstitutionId(1)
                .patientName("Laiane Carvalho de Oliveira")
                .patientAge(30)
                .patientGender("FEMALE")
                .physicianName("Wellton S. Barros")
                .physicianCRM(12548)
                .procedureName("Cirurgia Plastica")
                .build());

        String json = "{ \"patientName\": \"Laiane Carvalho de Oliveira\", \"patientAge\": 30, \"patientGender\": \"FEMALE\", \"physicianName\": \"Wellton S. Barros\", \"physicianCRM\": 146588, \"procedureName\": \"Cirurgia Plastica\", \"billed\": true}";
        this.mockMvc.perform(post("/createExam").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.healthcareInstitutionId").exists())
                .andExpect(jsonPath("$.patientName").exists())
                .andExpect(jsonPath("$.patientAge").exists())
                .andExpect(jsonPath("$.patientGender").exists())
                .andExpect(jsonPath("$.physicianName").exists())
                .andExpect(jsonPath("$.physicianCRM").exists())
                .andExpect(jsonPath("$.procedureName").exists())
                .andExpect(jsonPath("$.billed").exists());
    }

    @Test
    public void shouldReturnJsonExceptionWhenErros() throws Exception {
        Mockito.doThrow(new NegativeValuesNotAcceptedException()).when(createExamPresenter).create(any());

        String json = "{ \"patientName\": \"Laiane Carvalho de Oliveira\", \"patientAge\": 30, \"patientGender\": \"FEMALE\", \"physicianName\": \"Wellton S. Barros\", \"physicianCRM\": 146588, \"procedureName\": \"Cirurgia Plastica\", \"billed\": true}";
        this.mockMvc.perform(post("/createExam").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.cause").exists());
    }
}
