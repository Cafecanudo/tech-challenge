package com.pixeon.healthcare.instituitionservice.dataprovider.api.applicationservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "healthcare-application-service")
public interface ApplicationServiceOpenFeign {

    @GetMapping("/valueForCreateHealthcareInstitution")
    ValueDTO getValueForCreateHealthcareInstitution();
}
