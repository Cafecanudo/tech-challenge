package com.pixeon.healthcare.applicationservice.entrypoint.rest.getvalueforcreateexam;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("valueForCreateExam")
public class GetValueForCreateExamController implements GetValueForCreateExamControllerDocument {

    @Autowired
    private GetValueForCreateExamPresenter getValueForCreateExamPresenter;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValueForCreateExamDTO> get() {
        log.info("Obtido valor para criar um novo exame.");
        return ResponseEntity.ok(getValueForCreateExamPresenter.get());
    }
}
