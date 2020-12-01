package com.pixeon.healthcare.usecases.createhealthcareInstitution;

import com.pixeon.healthcare.domain.models.HealthcareInstitutionDTO;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.exception.CNPJEmptyException;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.exception.CNPJInvalidException;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.exception.NameCantEmptyException;
import com.pixeon.healthcare.usecases.getvalueconfigapplication.ApplicationConfigFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CreateHealthcareInstitutionFactoryTest {

    public static final double VALUE_FOR_NEW_INSTITUTION = 20;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private HealthcareInstitutionFactory institutionService;
    @Mock
    private ApplicationConfigFactory applicationConfigFactory;

    private CreateHealthcareInstitutionService createHealthcareInstitutionService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.createHealthcareInstitutionService = new CreateHealthcareInstitutionService(applicationConfigFactory, institutionService);
        when(institutionService.save(any(HealthcareInstitutionDTO.class))).thenAnswer((Answer<HealthcareInstitutionDTO>) invocationOnMock -> {
            HealthcareInstitutionDTO institution = invocationOnMock.getArgument(0);
            institution.setId(1);
            institution.setCoins(new BigDecimal(VALUE_FOR_NEW_INSTITUTION));
            return institution;
        });

        when(applicationConfigFactory.getValueForNewHealthcareInstitution()).thenReturn(new BigDecimal(VALUE_FOR_NEW_INSTITUTION));
    }

    @Test
    public void shouldCreateHealthcare() {
        HealthcareInstitutionDTO healthcareInstitutionDTO = new HealthcareInstitutionDTO.Builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build();

        healthcareInstitutionDTO = this.createHealthcareInstitutionService.create(healthcareInstitutionDTO);
        assertEquals(1, healthcareInstitutionDTO.getId());
        assertEquals("Instituição de Saúde", healthcareInstitutionDTO.getName());
        assertEquals("42.094.340/0001-79", healthcareInstitutionDTO.getCnpj());
        assertEquals(20.0d, healthcareInstitutionDTO.getCoins().doubleValue(), 1);
    }

    @Test
    public void shouldThrowExceptionWhenCreateHealthcareWithNameEmpty() {
        expectedException.expect(NameCantEmptyException.class);
        expectedException.expectMessage("Nome da instituição está em branco.");

        HealthcareInstitutionDTO healthcareInstitutionDTO = new HealthcareInstitutionDTO.Builder()
                .cnpj("42.094.340/0001-79")
                .build();

        this.createHealthcareInstitutionService.create(healthcareInstitutionDTO);
    }

    @Test
    public void shouldThrowExceptionWhenCreateHealthcareWithCNPJEmpty() {
        expectedException.expect(CNPJEmptyException.class);
        expectedException.expectMessage("CNPJ da instituição está em branco!");

        HealthcareInstitutionDTO healthcareInstitutionDTO = new HealthcareInstitutionDTO.Builder()
                .name("Instituição de Saúde")
                .build();

        this.createHealthcareInstitutionService.create(healthcareInstitutionDTO);
    }

    @Test
    public void shouldThrowExceptionWhenCreateHealthcareWithCNPJInvalid() {
        HealthcareInstitutionDTO healthcareInstitutionDTO = new HealthcareInstitutionDTO.Builder()
                .name("Instituição de Saúde")
                .cnpj("99.999.999/9999-99")
                .build();

        CNPJInvalidException exception = assertThrows(CNPJInvalidException.class,
                () -> this.createHealthcareInstitutionService.create(healthcareInstitutionDTO));

        assertEquals("CNPJ da instituição é inválido!", exception.getMessage());
    }
}
