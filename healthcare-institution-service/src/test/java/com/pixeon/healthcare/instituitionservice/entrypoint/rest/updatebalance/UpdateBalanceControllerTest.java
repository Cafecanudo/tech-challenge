package com.pixeon.healthcare.instituitionservice.entrypoint.rest.updatebalance;

import com.pixeon.healthcare.domain.config.exception.NegativeValuesNotAcceptedException;
import com.pixeon.healthcare.domain.config.exception.NoBalanceToCreateExamException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(UpdateBalanceController.class)
public class UpdateBalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UpdateBalancePresenter updateBalancePresenter;

    @Test
    public void shouldReturnStatusAcceptedWhenUpdated() throws Exception {
        doNothing().when(updateBalancePresenter).update(any(OperationDTO.class));

        String json = "{\"healthcareInstitutionId\":1,\"operation\":\"DEBIT\", \"value\": 200.0}";
        this.mockMvc.perform(put("/updateBalance").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldReturnJsonExceptionWhenInstitutionEnoughBalance() throws Exception {
        Mockito.doThrow(new NoBalanceToCreateExamException()).when(updateBalancePresenter).update(any(OperationDTO.class));

        String json = "{\"healthcareInstitutionId\":1,\"operation\":\"DEBIT\", \"value\": 200.0}";
        this.mockMvc.perform(put("/updateBalance").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isNotModified())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("Instituição não possui saldo para criar exame!"))
                .andExpect(jsonPath("$.cause").value("NoBalanceToCreateExamException"));
    }

    @Test
    public void shouldReturnJsonExceptionWhenNegativeValues() throws Exception {
        Mockito.doThrow(new NegativeValuesNotAcceptedException()).when(updateBalancePresenter).update(any(OperationDTO.class));

        String json = "{\"healthcareInstitutionId\":1,\"operation\":\"DEBIT\", \"value\": 200.0}";
        this.mockMvc.perform(put("/updateBalance").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("Valores negativos não são aceitos!"))
                .andExpect(jsonPath("$.cause").value("NegativeValuesNotAcceptedException"));
    }
}