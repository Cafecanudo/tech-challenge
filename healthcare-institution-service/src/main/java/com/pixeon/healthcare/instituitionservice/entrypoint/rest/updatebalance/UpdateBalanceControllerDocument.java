package com.pixeon.healthcare.instituitionservice.entrypoint.rest.updatebalance;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(tags = "Informações/ações para Exames")
@ApiResponses(
        value = {
                @ApiResponse(code = 202, message = "Registro atualizado"),
                @ApiResponse(code = 304, message = "Instituição não possui saldo!"),
                @ApiResponse(code = 400, message = "Não foi possível atualizar valor da instituição"),
                @ApiResponse(code = 401, message = "Acesso não autorizado"),
                @ApiResponse(code = 404, message = "Instituição não foi encontrada"),
                @ApiResponse(code = 404, message = "Instituição não foi encontrada"),
        }
)
public interface UpdateBalanceControllerDocument {

    @ApiOperation(value = "Atualiza o saldo de uma instituição de saúde", code = 202)
    ResponseEntity update(OperationDTO operationDTO);
}
