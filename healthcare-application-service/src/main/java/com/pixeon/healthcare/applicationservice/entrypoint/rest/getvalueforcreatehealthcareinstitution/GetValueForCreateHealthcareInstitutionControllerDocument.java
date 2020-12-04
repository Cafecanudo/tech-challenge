package com.pixeon.healthcare.applicationservice.entrypoint.rest.getvalueforcreatehealthcareinstitution;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(tags = "Informações/ações de configuração do sistema")
@ApiResponses(
        value = {
                @ApiResponse(code = 201, message = "Registro criado"),
                @ApiResponse(code = 204, message = "Sem retorno"),
                @ApiResponse(code = 400, message = "Parametros inválidos"),
                @ApiResponse(code = 401, message = "Acesso não autorizado"),
                @ApiResponse(code = 404, message = "Registro(s) não encontrado(s)"),
                @ApiResponse(code = 409, message = "Registro duplicado"),
        }
)
public interface GetValueForCreateHealthcareInstitutionControllerDocument {

    @ApiOperation(value = "Obter valor para criação de uma nova Instituição de saúde", response = ValueForNewHealthcareInstitutionDTO.class)
    ResponseEntity<ValueForNewHealthcareInstitutionDTO> get();
}
