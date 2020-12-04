package com.pixeon.healthcare.examservice.dataprovider.api.institutionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "healthcare-institution-service")
public interface InstitutionServiceOpenFeign {

    @PutMapping("/updateBalance")
    void updateBalance(@RequestBody OperationDTO operationDTO);

}
