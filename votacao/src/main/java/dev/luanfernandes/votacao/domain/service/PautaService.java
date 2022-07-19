package dev.luanfernandes.votacao.domain.service;

import dev.luanfernandes.votacao.api.exceptions.NotDeleteException;
import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.domain.entity.Pauta;

import java.util.List;

public interface PautaService {
    Pauta criar(Pauta pauta);

    void atualizar(Pauta pauta) throws NotFoundException;

    void deletar(Long id) throws NotFoundException, NotDeleteException;

    List<Pauta> obterTodos();

    Pauta obterPorId(Long id) throws NotFoundException;

    boolean verificaExistePautaPorId(Long id);
}
