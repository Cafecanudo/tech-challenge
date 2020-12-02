package com.pixeon.healthcare.instituitionservice.entrypoint.rest.createInstitution;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Informações/ações para Instituição de saúde")
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
public interface CreateHealthcareInstitutionControllerDocument {

    @ApiOperation(value = "Salva uma nova instituição de saúde", response = HealthcareInstitutionDTO.class)
    ResponseEntity<HealthcareInstitutionDTO> create(@RequestBody HealthcareInstitutionDTO healthcareInstitutionDTO);

}
