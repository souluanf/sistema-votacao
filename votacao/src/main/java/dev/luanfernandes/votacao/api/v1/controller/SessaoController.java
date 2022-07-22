package dev.luanfernandes.votacao.api.v1.controller;

import dev.luanfernandes.votacao.api.exceptions.DateTimeException;
import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.api.v1.controller.openapi.SessaoControllerOpenApi;
import dev.luanfernandes.votacao.domain.dto.ResultadoSessaoDTO;
import dev.luanfernandes.votacao.domain.dto.SessaoDTO;
import dev.luanfernandes.votacao.domain.entity.ResultadoSessao;
import dev.luanfernandes.votacao.domain.entity.Sessao;
import dev.luanfernandes.votacao.domain.service.SessaoService;
import dev.luanfernandes.votacao.infrastructure.implementation.SessaoServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Sessão")
@RestController
@RequestMapping(value = "/v1/sessoes")
@Slf4j
public class SessaoController implements SessaoControllerOpenApi {
	private final SessaoService sessaoService;
	public SessaoController(SessaoServiceImpl sessaoService) {
		this.sessaoService = sessaoService;
	}
	@Override
	@PostMapping
	public SessaoDTO abrirSessao(@Valid @RequestBody SessaoDTO sessao)
			throws DateTimeException, NotFoundException {
		log.debug("REST requisição para salvar sessão: {}", sessao);
		Sessao created = this.sessaoService.abrir(SessaoDTO.toSessao(sessao));
		return SessaoDTO.from(created);
	}
	@Override
	@GetMapping(value = "/{id}")
	public SessaoDTO obterPorId(@PathVariable("id") Long id){
		log.debug("REST requisição para obter sessão: {}", id);
		return SessaoDTO.from(this.sessaoService.obterPorId(id));
	}
	@Override
	@GetMapping
	public List<SessaoDTO> obterTodos() {
		return this.sessaoService.obterTodos().stream().map(SessaoDTO::from).toList();
	}
	@Override
	@GetMapping(value = "/{id}/resultado")
	public ResultadoSessaoDTO obterResultado(@PathVariable("id") Long id){
		ResultadoSessao resultadoSessao = this.sessaoService.obterResultadoPorIdSessao(id);
		return ResultadoSessaoDTO.from(resultadoSessao);
	}
}