package com.pixeon.healthcare.examservice.dataprovider.repository;

import com.pixeon.healthcare.examservice.config.entity.Exam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends CrudRepository<Exam, Integer> {

}
