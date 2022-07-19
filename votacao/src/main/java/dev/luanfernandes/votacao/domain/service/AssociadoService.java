package dev.luanfernandes.votacao.domain.service;

import dev.luanfernandes.votacao.api.exceptions.ConflictException;
import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.domain.entity.Associado;

import java.util.List;
import java.util.Optional;

public interface AssociadoService {
    Associado criar(Associado associado) throws ConflictException;
    void deletar(Long id) throws NotFoundException;
    Associado obterPorId(Long id);
    List<Associado> obterTodos();
    Optional<Associado> obterPorCPF(String cpf);
    boolean verificaExistePorId(Long id);
}
