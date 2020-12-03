package com.pixeon.healthcare.applicationservice.entrypoint.rest.getvalueforconsultingexam;

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
@WebMvcTest(GetValueForConsultingExamController.class)
public class GetValueForConsultingExamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetValueForConsultingExamPresenter getValueForConsultingExamPresenter;

    @Test
    public void returnsValorForConsultingExam() throws Exception {
        when(getValueForConsultingExamPresenter.get())
                .thenReturn(ValueForConsultingExamDTO.builder().value(new BigDecimal(20.0)).build());

        this.mockMvc.perform(get("/valueForConsultingExam"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").exists())
                .andExpect(jsonPath("$.value").value(20));
    }
}