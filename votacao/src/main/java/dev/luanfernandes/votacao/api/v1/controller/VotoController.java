package dev.luanfernandes.votacao.api.v1.controller;

import dev.luanfernandes.votacao.api.v1.controller.openapi.VotoControllerOpenApi;
import dev.luanfernandes.votacao.domain.dto.VotoDTO;
import dev.luanfernandes.votacao.infrastructure.implementation.VotoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/votos")
@Slf4j
public class VotoController implements VotoControllerOpenApi {
	private final VotoServiceImpl votoServiceImpl;

	public VotoController(VotoServiceImpl votoServiceImpl) {
		this.votoServiceImpl = votoServiceImpl;
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String,String> votar(@Valid @RequestBody VotoDTO voto){
		this.votoServiceImpl.votar(voto.getIdSessao(), voto.getCpf(), voto.getValor());
		Map<String,String> myMap = new HashMap<>();
		myMap.put("status", "voto registrado com sucesso.");
		return myMap;
	}
}