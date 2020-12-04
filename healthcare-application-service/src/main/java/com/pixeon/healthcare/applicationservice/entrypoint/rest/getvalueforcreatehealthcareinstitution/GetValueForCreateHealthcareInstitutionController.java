package com.pixeon.healthcare.applicationservice.entrypoint.rest.getvalueforcreatehealthcareinstitution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("valueForCreateHealthcareInstitution")
public class GetValueForCreateHealthcareInstitutionController implements GetValueForCreateHealthcareInstitutionControllerDocument {

    @Autowired
    private GetValueForCreateHealthcareInstitutionPresenter getValueForCreateHealthcareInstitutionPresenter;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValueForNewHealthcareInstitutionDTO> get() {
        log.info("Obtido valor para criar nova instituição de saúde.");
        return ResponseEntity.ok(getValueForCreateHealthcareInstitutionPresenter.get());
    }
}
