package com.pixeon.healthcare.domain.usecase.createexam;

import com.pixeon.healthcare.domain.model.ExamModel;

public interface ExamGateway {

    ExamModel save(ExamModel examModel);

    ExamModel update(ExamModel examModel);

    boolean delete(ExamModel examModel);

    ExamModel getExameById(int examId);
}
