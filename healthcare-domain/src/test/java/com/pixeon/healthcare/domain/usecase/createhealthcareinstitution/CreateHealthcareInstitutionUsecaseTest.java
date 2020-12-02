package com.pixeon.healthcare.domain.usecase.createhealthcareinstitution;

import com.pixeon.healthcare.domain.config.exception.CNPJEmptyException;
import com.pixeon.healthcare.domain.config.exception.CNPJInvalidException;
import com.pixeon.healthcare.domain.config.exception.NameCantEmptyException;
import com.pixeon.healthcare.domain.entity.HealthcareInstitution;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.impl.CreateHealthcareInstitutionUsecaseImpl;
import com.pixeon.healthcare.domain.usecase.getvalueconfigapplication.ApplicationConfigGateway;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CreateHealthcareInstitutionUsecaseTest {

    public static final double VALUE_FOR_NEW_INSTITUTION = 20;
    @Mock
    private HealthcareInstitutionGateway institutionService;
    @Mock
    private ApplicationConfigGateway applicationConfigGateway;

    private CreateHealthcareInstitutionUsecase createHealthcareInstitutionUsecase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.createHealthcareInstitutionUsecase = new CreateHealthcareInstitutionUsecaseImpl(applicationConfigGateway, institutionService);
        when(institutionService.save(any(HealthcareInstitution.class))).thenAnswer((Answer<HealthcareInstitution>) invocationOnMock -> {
            HealthcareInstitution institution = invocationOnMock.getArgument(0);
            institution.setId(1);
            institution.setCoin(new BigDecimal(VALUE_FOR_NEW_INSTITUTION));
            return institution;
        });

        when(applicationConfigGateway.getValueForNewHealthcareInstitution()).thenReturn(new BigDecimal(VALUE_FOR_NEW_INSTITUTION));
    }

    @Test
    public void shouldCreateHealthcare() {
        HealthcareInstitution healthcareInstitution = HealthcareInstitution.builder()
                .name("Instituição de Saúde")
                .cnpj("42.094.340/0001-79")
                .build();

        healthcareInstitution = this.createHealthcareInstitutionUsecase.create(healthcareInstitution);
        assertEquals(1, healthcareInstitution.getId());
        assertEquals("Instituição de Saúde", healthcareInstitution.getName());
        assertEquals("42.094.340/0001-79", healthcareInstitution.getCnpj());
        assertEquals(20.0d, healthcareInstitution.getCoin().doubleValue(), 1);
    }

    @Test
    public void shouldThrowExceptionWhenCreateHealthcareWithNameEmpty() {
        HealthcareInstitution healthcareInstitution = HealthcareInstitution.builder()
                .cnpj("42.094.340/0001-79")
                .build();

        NameCantEmptyException exception = assertThrows(NameCantEmptyException.class,
                () -> this.createHealthcareInstitutionUsecase.create(healthcareInstitution));
        assertEquals("Nome da instituição está em branco.", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenCreateHealthcareWithCNPJEmpty() {
        HealthcareInstitution healthcareInstitution = HealthcareInstitution.builder()
                .name("Instituição de Saúde")
                .build();

        CNPJEmptyException exception = assertThrows(CNPJEmptyException.class,
                () -> this.createHealthcareInstitutionUsecase.create(healthcareInstitution));
        assertEquals("CNPJ da instituição está em branco!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenCreateHealthcareWithCNPJInvalid() {
        HealthcareInstitution healthcareInstitution = HealthcareInstitution.builder()
                .name("Instituição de Saúde")
                .cnpj("99.999.999/9999-99")
                .build();

        CNPJInvalidException exception = assertThrows(CNPJInvalidException.class,
                () -> this.createHealthcareInstitutionUsecase.create(healthcareInstitution));

        assertEquals("CNPJ da instituição é inválido!", exception.getMessage());
    }
}
