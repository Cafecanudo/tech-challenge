package com.pixeon.healthcare.usecases.createexam;

import com.pixeon.healthcare.models.ExamModel;

public interface ExamService {

    ExamModel save(ExamModel examModel);

    ExamModel update(ExamModel examModel);

    boolean delete(ExamModel examModel);

    ExamModel getExameById(int examId);
}
