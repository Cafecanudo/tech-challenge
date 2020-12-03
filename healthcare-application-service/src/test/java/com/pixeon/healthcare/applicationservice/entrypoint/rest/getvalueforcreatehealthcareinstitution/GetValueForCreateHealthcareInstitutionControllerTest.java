package com.pixeon.healthcare.applicationservice.entrypoint.rest.getvalueforcreatehealthcareinstitution;

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
@WebMvcTest(GetValueForCreateHealthcareInstitutionController.class)
public class GetValueForCreateHealthcareInstitutionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetValueForCreateHealthcareInstitutionPresenter getValueForCreateHealthcareInstitutionPresenter;

    @Test
    public void returnsValueToCreateNewHealthInstitution() throws Exception {
        when(getValueForCreateHealthcareInstitutionPresenter.get())
                .thenReturn(ValueForNewHealthcareInstitutionDTO.builder().value(new BigDecimal(20.0)).build());

        this.mockMvc.perform(get("/valueForNewHealthcareInstitution"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").exists())
                .andExpect(jsonPath("$.value").value(20));
    }
}