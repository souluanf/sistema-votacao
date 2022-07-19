package dev.luanfernandes.votacao.api.v1.controller.openapi;

import dev.luanfernandes.votacao.api.exceptions.handler.StandardError;
import dev.luanfernandes.votacao.domain.dto.PautaDTO;
import io.swagger.annotations.*;

import java.util.List;


@Api(tags = "Pauta")
public interface PautaControllerOpenApi {
    @ApiOperation(value = "Cria uma nova pauta")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cadastro realizada com sucesso", response = PautaDTO.class),
            @ApiResponse(code = 400, message = "Requisição mal formada", response = StandardError.class),
            @ApiResponse(code = 500, message = "Erro interno do servidor", response = StandardError.class),
    })
    PautaDTO criar(PautaDTO pauta);

    @ApiOperation(value = "Obtém uma pauta por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Requisição realizada com sucesso", response = PautaDTO.class),
            @ApiResponse(code = 400, message = "Requisição mal formada", response = StandardError.class),
            @ApiResponse(code = 404, message = "Pauta inexistente", response = StandardError.class),
            @ApiResponse(code = 500, message = "Erro interno do servidor", response = StandardError.class),
    })
    PautaDTO obterPorId(@ApiParam(value = "PautaId",example = "1") Long id);

    @ApiOperation(value = "Obtém todas as pautas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Requisição realizada com sucesso", response = PautaDTO.class),
    })
    List<PautaDTO> obterTodos();
}
