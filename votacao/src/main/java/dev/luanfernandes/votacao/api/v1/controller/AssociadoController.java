package dev.luanfernandes.votacao.api.v1.controller;


import dev.luanfernandes.votacao.api.v1.controller.openapi.AssociadoControllerOpenApi;
import dev.luanfernandes.votacao.domain.dto.AssociadoDTO;
import dev.luanfernandes.votacao.domain.entity.Associado;
import dev.luanfernandes.votacao.infrastructure.implementation.AssociadoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/associados")
@Slf4j
public class AssociadoController implements AssociadoControllerOpenApi {

	private final AssociadoServiceImpl associadoServiceImpl;

	public AssociadoController(AssociadoServiceImpl associadoServiceImpl) {
		this.associadoServiceImpl = associadoServiceImpl;
	}

	@Override
	@PostMapping
	public AssociadoDTO criar(@RequestBody AssociadoDTO associado){
		Associado associadoSalvo = this.associadoServiceImpl.criar(AssociadoDTO.toAssociado(associado));
		return AssociadoDTO.from(associadoSalvo);
	}

	@Override
	@GetMapping(value = "/{id}")
	public AssociadoDTO obterPorId(@PathVariable("id") Long id) {
		return AssociadoDTO.from(this.associadoServiceImpl.obterPorId(id));
	}

	@Override
	@GetMapping
	public List<AssociadoDTO> obterTodos() {
		return this.associadoServiceImpl.obterTodos().stream().map(AssociadoDTO::from).toList();
	}
}