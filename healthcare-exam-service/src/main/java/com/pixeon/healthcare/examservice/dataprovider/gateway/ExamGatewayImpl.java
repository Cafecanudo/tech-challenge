package com.pixeon.healthcare.examservice.dataprovider.gateway;

import com.pixeon.healthcare.domain.model.ExamModel;
import com.pixeon.healthcare.domain.usecase.createexam.ExamGateway;
import com.pixeon.healthcare.examservice.adapter.ExamEntityAdapter;
import com.pixeon.healthcare.examservice.adapter.ExamModelAdapter;
import com.pixeon.healthcare.examservice.config.entity.Exam;
import com.pixeon.healthcare.examservice.dataprovider.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExamGatewayImpl implements ExamGateway {

    @Autowired
    private ExamRepository examRepository;

    @Override
    public ExamModel save(ExamModel examModel) {
        Exam exam = examRepository.save(ExamModelAdapter.toEntity(examModel));
        return ExamEntityAdapter.toModel(exam);
    }
}
