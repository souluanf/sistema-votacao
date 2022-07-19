package dev.luanfernandes.votacao.api.v1.controller.openapi;

import dev.luanfernandes.votacao.api.exceptions.handler.StandardError;
import dev.luanfernandes.votacao.domain.dto.VotoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Map;

@Api(tags = "Voto")
public interface VotoControllerOpenApi {
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Voto realizado com sucesso", response = void.class),
            @ApiResponse(code = 400, message = "Requisição mal formada", response = StandardError.class),
            @ApiResponse(code = 404, message = "não encontrado", response = StandardError.class),
    })
    Map<String,String> votar(VotoDTO voto);
}
