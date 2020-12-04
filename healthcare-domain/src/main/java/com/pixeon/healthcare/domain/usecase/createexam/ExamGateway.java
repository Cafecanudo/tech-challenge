package com.pixeon.healthcare.domain.usecase.createexam;

import com.pixeon.healthcare.domain.model.ExamModel;

public interface ExamGateway {

    default ExamModel save(ExamModel examModel) {
        throw new RuntimeException("Not implemented");
    }

    default ExamModel update(ExamModel examModel) {
        throw new RuntimeException("Not implemented");
    }

    default boolean delete(ExamModel examModel) {
        throw new RuntimeException("Not implemented");
    }

    default ExamModel getExameById(int examId) {
        throw new RuntimeException("Not implemented");
    }
}
