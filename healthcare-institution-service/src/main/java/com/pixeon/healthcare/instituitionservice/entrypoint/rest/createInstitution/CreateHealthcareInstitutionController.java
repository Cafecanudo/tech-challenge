package com.pixeon.healthcare.instituitionservice.entrypoint.rest.createInstitution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "createHealthcareInstitution")
public class CreateHealthcareInstitutionController implements CreateHealthcareInstitutionControllerDocument {

    @Autowired
    private CreateHealthcareInstitutionPresenter createHealthcareInstitutionPresenter;

    @Override
    @PostMapping
    public ResponseEntity<HealthcareInstitutionDTO> create(@Valid @RequestBody HealthcareInstitutionDTO healthcareInstitutionDTO) {
        log.info("Criando nova instituição de saúde...");
        return ResponseEntity.status(HttpStatus.CREATED).body(createHealthcareInstitutionPresenter.create(healthcareInstitutionDTO));
    }
}
