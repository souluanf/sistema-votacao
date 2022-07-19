package dev.luanfernandes.votacao.api.v1.controller.openapi;

import dev.luanfernandes.votacao.api.exceptions.handler.StandardError;
import dev.luanfernandes.votacao.domain.dto.ResultadoSessaoDTO;
import dev.luanfernandes.votacao.domain.dto.SessaoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api(tags = "Sessão")
public interface SessaoControllerOpenApi {
    @ApiOperation(value = "Abre uma nova sessão para a pauta")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Sessão aberta com sucesso", response = SessaoDTO.class),
            @ApiResponse(code = 400, message = "Requisição mal formada", response = StandardError.class),
            @ApiResponse(code = 404, message = "Pauta inexistente", response = StandardError.class),
    })
    SessaoDTO abrirSessao(SessaoDTO sessao);

    @ApiOperation(value = "Obtém uma sessão pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Requisição realizada com sucesso", response = SessaoDTO.class),
            @ApiResponse(code = 400, message = "Requisição mal formada", response = StandardError.class),
            @ApiResponse(code = 404, message = "Sessão inexistente", response = StandardError.class),
    })
    SessaoDTO obterPorId(Long id);

    @ApiOperation(value = "Obtém todas as sessões")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Requisição realizada com sucesso", response = SessaoDTO.class),
    })
    List<SessaoDTO> obterTodos();

    @ApiOperation(value = "Obtém o resultado parcial ou final da sessão")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Requisição realizada com sucesso", response = ResultadoSessaoDTO.class),
            @ApiResponse(code = 404, message = "Dado não encontrado", response = StandardError.class),
    })
    ResultadoSessaoDTO obterResultado(Long id);
}
