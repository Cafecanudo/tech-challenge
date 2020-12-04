package com.pixeon.healthcare.examservice.entrypoint.rest.createexam;

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
@RequestMapping(value = "createExam")
public class CreateExamController implements CreateExamControllerDocument {

    @Autowired
    private CreateExamPresenter createExamPresenter;

    @Override
    @PostMapping
    public ResponseEntity<ExamDTO> create(@Valid @RequestBody ExamDTO examDTO) {
        log.info("Criando um exame para instituição de saúde...");
        return ResponseEntity.status(HttpStatus.CREATED).body(createExamPresenter.create(examDTO));
    }
}
