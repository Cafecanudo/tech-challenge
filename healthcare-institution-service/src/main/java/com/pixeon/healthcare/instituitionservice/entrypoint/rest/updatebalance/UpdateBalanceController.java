package com.pixeon.healthcare.instituitionservice.entrypoint.rest.updatebalance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "updateBalance")
public class UpdateBalanceController implements UpdateBalanceControllerDocument {

    @Autowired
    private UpdateBalancePresenter updateBalancePresenter;

    @Override
    @PutMapping
    public ResponseEntity update(@Valid @RequestBody OperationDTO operationDTO) {
        log.info("Atualizando o valor de uma institução de saúde...");
        updateBalancePresenter.update(operationDTO);
        return ResponseEntity.accepted().build();
    }
}
