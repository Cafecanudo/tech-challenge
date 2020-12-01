package com.pixeon.healthcare.usecase.getvaluefornewhealthcareinstitution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("valueForNewHealthcareInstitution")
public class GetValueForNewHealthcareInstitutionController implements GetValueForNewHealthcareInstitutionControllerDocument {

    @Autowired
    private GetValueForNewHealthcareInstitutionService getValueForNewHealthcareInstitutionService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValueForNewHealthcareInstitutionDTO> get() {
        log.info("Obtido valor para criar nova instituição de saúde.");
        return ResponseEntity.ok(getValueForNewHealthcareInstitutionService.get());
    }
}
