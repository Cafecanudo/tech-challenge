package com.pixeon.healthcare.examservice.entrypoint.rest.createexam;

import com.pixeon.healthcare.domain.model.ExamModel;
import com.pixeon.healthcare.domain.usecase.createexam.CreateExamUsecase;
import com.pixeon.healthcare.examservice.adapter.ExamDTOAdapter;
import com.pixeon.healthcare.examservice.adapter.ExamModelAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateExamPresenter {

    @Autowired
    private CreateExamUsecase createExamUsecase;

    public ExamDTO create(ExamDTO examDTO) {
        ExamModel exam = createExamUsecase.create(ExamDTOAdapter.toModel(examDTO));
        return ExamModelAdapter.toDTO(exam);
    }
}
