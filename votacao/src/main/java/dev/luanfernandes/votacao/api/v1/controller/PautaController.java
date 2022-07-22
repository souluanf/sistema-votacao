package dev.luanfernandes.votacao.api.v1.controller;

import dev.luanfernandes.votacao.api.v1.controller.openapi.PautaControllerOpenApi;
import dev.luanfernandes.votacao.domain.dto.PautaDTO;
import dev.luanfernandes.votacao.domain.entity.Pauta;
import dev.luanfernandes.votacao.infrastructure.implementation.PautaServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Pauta")
@RestController
@RequestMapping(value = "/v1/pautas")
@Slf4j
public class PautaController implements PautaControllerOpenApi {

	public final PautaServiceImpl pautaServiceImpl;

	public PautaController(PautaServiceImpl pautaServiceImpl) {
		this.pautaServiceImpl = pautaServiceImpl;
	}

	@Override
	@PostMapping
	public PautaDTO criar(@Valid @RequestBody PautaDTO pauta) {
		Pauta pautaSalvo = this.pautaServiceImpl.criar(PautaDTO.toPauta(pauta));
		return PautaDTO.from(pautaSalvo);
	}

	@Override
	@GetMapping(value = "/{id}")
	public PautaDTO obterPorId(@PathVariable("id") Long id){
		return PautaDTO.from(this.pautaServiceImpl.obterPorId(id));
	}

	@Override
	@GetMapping
	public List<PautaDTO> obterTodos() {
		return this.pautaServiceImpl.obterTodos().stream().map(PautaDTO::from).toList();
	}
}