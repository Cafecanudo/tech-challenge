package com.pixeon.healthcare.applicationservice.entrypoint.rest.getvalueforcreateexam;

import com.pixeon.healthcare.applicationservice.config.entity.Configuration;
import com.pixeon.healthcare.applicationservice.dataprovider.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetValueForCreateExamPresenter {

    @Autowired
    private ApplicationRepository applicationRepository;

    public ValueForCreateExamDTO get() {
        Configuration configuration = applicationRepository.findAll().iterator().next();
        return ValueForCreateExamDTO.builder().value(configuration.getValueForCreateExam()).build();
    }
}
