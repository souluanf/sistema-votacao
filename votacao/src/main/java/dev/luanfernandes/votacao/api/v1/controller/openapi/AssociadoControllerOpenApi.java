package dev.luanfernandes.votacao.api.v1.controller.openapi;

import dev.luanfernandes.votacao.api.exceptions.handler.StandardError;
import dev.luanfernandes.votacao.domain.dto.AssociadoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api(tags = "Associado")
public interface AssociadoControllerOpenApi {
    @ApiOperation(value = "Cria um novo associado")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cadastro realizado com sucesso", response = AssociadoDTO.class),
            @ApiResponse(code = 409, message = "CPF já cadastrado", response = StandardError.class),
            @ApiResponse(code = 400, message = "Requisição mal formada", response = StandardError.class),
    })
    AssociadoDTO criar(AssociadoDTO associado);

    @ApiOperation(value = "Obtém um associado por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Requisição realizada com sucesso", response = AssociadoDTO.class),
            @ApiResponse(code = 400, message = "Requisição mal formada", response = StandardError.class),
            @ApiResponse(code = 404, message = "Associado inexistente", response = StandardError.class),
    })
    AssociadoDTO obterPorId(Long id);

    @ApiOperation(value = "Obtém todos os associados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Requisição realizada com sucesso", response = AssociadoDTO.class),
            @ApiResponse(code = 500, message = "Erro interno do servidor", response = StandardError.class),
    })
    List<AssociadoDTO> obterTodos();
}
