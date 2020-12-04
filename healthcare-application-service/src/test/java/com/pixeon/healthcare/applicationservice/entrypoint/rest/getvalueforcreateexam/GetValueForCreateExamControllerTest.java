package com.pixeon.healthcare.applicationservice.entrypoint.rest.getvalueforcreateexam;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(GetValueForCreateExamController.class)
public class GetValueForCreateExamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetValueForCreateExamPresenter getValueForCreateExamPresenter;

    @Test
    public void returnsValorForConsultingExam() throws Exception {
        when(getValueForCreateExamPresenter.get())
                .thenReturn(ValueForCreateExamDTO.builder().value(new BigDecimal(20.0)).build());

        this.mockMvc.perform(get("/valueForCreateExam"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").exists())
                .andExpect(jsonPath("$.value").value(20));
    }
}