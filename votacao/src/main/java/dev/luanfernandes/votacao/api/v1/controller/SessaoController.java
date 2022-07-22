package dev.luanfernandes.votacao.api.v1.controller;

import dev.luanfernandes.votacao.api.exceptions.DateTimeException;
import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.api.v1.controller.openapi.SessaoControllerOpenApi;
import dev.luanfernandes.votacao.domain.dto.ResultadoSessaoDTO;
import dev.luanfernandes.votacao.domain.dto.SessaoDTO;
import dev.luanfernandes.votacao.domain.entity.ResultadoSessao;
import dev.luanfernandes.votacao.domain.entity.Sessao;
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

	private final SessaoServiceImpl sessaoServiceImpl;

	public SessaoController(SessaoServiceImpl sessaoServiceImpl) {
		this.sessaoServiceImpl = sessaoServiceImpl;
	}

	@Override
	@PostMapping
	public SessaoDTO abrirSessao(@Valid @RequestBody SessaoDTO sessao)
			throws DateTimeException, NotFoundException {
		log.debug("REST requisição para salvar sessão: {}", sessao);
		Sessao created = this.sessaoServiceImpl.abrir(SessaoDTO.toSessao(sessao));
		return SessaoDTO.from(created);
	}

	@Override
	@GetMapping(value = "/{id}")
	public SessaoDTO obterPorId(@PathVariable("id") Long id){
		log.debug("REST requisição para obter sessão: {}", id);
		return SessaoDTO.from(this.sessaoServiceImpl.obterPorId(id));
	}

	@Override
	@GetMapping
	public List<SessaoDTO> obterTodos() {
		return this.sessaoServiceImpl.obterTodos().stream().map(SessaoDTO::from).toList();
	}

	@Override
	@GetMapping(value = "/{id}/resultado")
	public ResultadoSessaoDTO obterResultado(@PathVariable("id") Long id){
		ResultadoSessao resultadoSessao = this.sessaoServiceImpl.obterResultadoPorIdSessao(id);
		return ResultadoSessaoDTO.from(resultadoSessao);
	}
}