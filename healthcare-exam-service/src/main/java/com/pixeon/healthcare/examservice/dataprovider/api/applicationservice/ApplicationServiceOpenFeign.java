package com.pixeon.healthcare.examservice.dataprovider.api.applicationservice;

import com.pixeon.healthcare.examservice.config.dto.ValueDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "healthcare-application-service")
public interface ApplicationServiceOpenFeign {

    @GetMapping("/valueForConsultingExam")
    ValueDTO getValueForConsultingExam();

    @GetMapping("/valueForCreateExam")
    ValueDTO getValueForCreateExam();

}
