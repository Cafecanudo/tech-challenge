package com.pixeon.healthcare.applicationservice.entrypoint.rest.getvalueforconsultingexam;

import com.pixeon.healthcare.applicationservice.config.entity.Configuration;
import com.pixeon.healthcare.applicationservice.dataprovider.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetValueForConsultingExamPresenter {

    @Autowired
    private ApplicationRepository applicationRepository;

    public ValueForConsultingExamDTO get() {
        Configuration configuration = applicationRepository.findAll().iterator().next();
        return ValueForConsultingExamDTO.builder().value(configuration.getValueForConsultingExam()).build();
    }
}
