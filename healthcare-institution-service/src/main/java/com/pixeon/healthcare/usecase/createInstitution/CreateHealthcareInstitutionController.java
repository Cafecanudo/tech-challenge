package com.pixeon.healthcare.usecase.createInstitution;

import com.pixeon.healthcare.domain.models.HealthcareInstitutionDTO;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.CreateHealthcareInstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "createHealthcareInstitution")
public class CreateHealthcareInstitutionController implements CreateHealthcareInstitutionControllerDocument {

    @Autowired
    private CreateHealthcareInstitutionService service;

    @Override
    @PostMapping
    public ResponseEntity<HealthcareInstitutionDTO> create(@RequestBody HealthcareInstitutionDTO healthcareInstitutionDTO) {
        log.info("Criando nova instituição de saúde...");
        return ResponseEntity.ok(service.create(healthcareInstitutionDTO));
    }
}
