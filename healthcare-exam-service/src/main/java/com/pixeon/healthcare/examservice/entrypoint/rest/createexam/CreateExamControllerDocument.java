package com.pixeon.healthcare.examservice.entrypoint.rest.createexam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(tags = "Informações/ações para Exames")
@ApiResponses(
        value = {
                @ApiResponse(code = 201, message = "Registro criado"),
                @ApiResponse(code = 204, message = "Sem retorno"),
                @ApiResponse(code = 304, message = "Instituição não possui saldo para criar exames"),
                @ApiResponse(code = 400, message = "Parametros inválidos"),
                @ApiResponse(code = 401, message = "Acesso não autorizado"),
                @ApiResponse(code = 404, message = "Registro(s) não encontrado(s)"),
        }
)
public interface CreateExamControllerDocument {

    @ApiOperation(value = "Salva uma nova instituição de saúde", response = ExamDTO.class)
    ResponseEntity<ExamDTO> create(ExamDTO examDTO);
}
