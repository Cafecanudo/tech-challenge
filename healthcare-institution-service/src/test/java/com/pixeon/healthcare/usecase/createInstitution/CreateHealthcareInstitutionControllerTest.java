package com.pixeon.healthcare.usecase.createInstitution;

import com.pixeon.healthcare.domain.models.HealthcareInstitutionDTO;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.CreateHealthcareInstitutionService;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.exception.CNPJInvalidException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(CreateHealthcareInstitutionController.class)
public class CreateHealthcareInstitutionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateHealthcareInstitutionService createHealthcareInstitutionService;

    @Test
    public void retornaJsonNovaInstituicaoCriada() throws Exception {
        when(createHealthcareInstitutionService.create(any(HealthcareInstitutionDTO.class)))
                .then((Answer<HealthcareInstitutionDTO>) invocationOnMock -> {
                    HealthcareInstitutionDTO dto = invocationOnMock.getArgument(0);
                    dto.setId(10);
                    dto.setCoins(new BigDecimal(21.0));
                    return dto;
                });

        String json = "{ \"name\":\"Instituição de saúde Maternal\", \"cnpj\":\"06.476.077/0001-56\" }";
        this.mockMvc.perform(post("/createHealthcareInstitution").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.cnpj").exists())
                .andExpect(jsonPath("$.coins").exists())
                .andExpect(jsonPath("$.validCNPJ").exists())
                .andExpect(jsonPath("$.emptyOrBlank").exists())
                .andExpect(jsonPath("$.emptyOrBlankCNPJ").exists())
                .andExpect(jsonPath("$.coins").value(21.0));
    }

    @Test
    public void retornaJsonDaExcecaoQuandoOcorreErro400() throws Exception {
        when(createHealthcareInstitutionService.create(any()))
                .thenThrow(new CNPJInvalidException());

        String json = "{ \"name\":\"Instituição de saúde Maternal\", \"cnpj\":\"06.476.077/0001-56\" }";
        this.mockMvc.perform(post("/createHealthcareInstitution").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("CNPJ da instituição é inválido!"))
                .andExpect(jsonPath("$.cause").value("CNPJInvalidException"));
    }

    @Test
    public void retornaJsonExcecaoQuandoOcorreErroInesperado() throws Exception {
        when(createHealthcareInstitutionService.create(any())).thenThrow(new NullPointerException("Algo ocorreu!"));

        String json = "{ \"name\":\"Instituição de saúde Maternal\" }";
        this.mockMvc.perform(post("/createHealthcareInstitution").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.cause").exists())
                .andExpect(jsonPath("$.message").value("Algo ocorreu!"))
                .andExpect(jsonPath("$.cause").value("NullPointerException"));
    }
}