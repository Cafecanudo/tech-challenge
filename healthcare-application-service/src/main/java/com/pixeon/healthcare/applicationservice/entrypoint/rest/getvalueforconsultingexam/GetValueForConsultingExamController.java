package com.pixeon.healthcare.applicationservice.entrypoint.rest.getvalueforconsultingexam;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("valueForConsultingExam")
public class GetValueForConsultingExamController implements GetValueForConsultingExamControllerDocument {

    @Autowired
    private GetValueForConsultingExamPresenter getValueForConsultingExamPresenter;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValueForConsultingExamDTO> get() {
        log.info("Obtido valor para consultar exames.");
        return ResponseEntity.ok(getValueForConsultingExamPresenter.get());
    }
}
