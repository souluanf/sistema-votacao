package dev.luanfernandes.votacao.domain.service;

import dev.luanfernandes.votacao.api.exceptions.ConflictException;
import dev.luanfernandes.votacao.api.exceptions.DateTimeException;
import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.domain.entity.Voto;

public interface VotoService {
    Voto votar(Long idSessao, String cpf, Boolean valor) throws NotFoundException, DateTimeException, ConflictException;

    boolean verificaExistePorIdSessaoIdAssociado(Long idSessao, Long idAssociado);
}
