package com.pixeon.healthcare.usecase.getValueForNewHealtcareInstution;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "healthcare-application-service")
public interface GetValueForNewHealthcareInstitutionOpenFeign {

    @GetMapping("/valueForNewHealthcareInstitution")
    ValueForNewHealthcareInstitutionDTO get();
}
