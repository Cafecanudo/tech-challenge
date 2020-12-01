package com.pixeon.healthcare.domain.usecases.deleteexam;

@FunctionalInterface
public interface DeleteExam {

    boolean delete(int examId);
}
