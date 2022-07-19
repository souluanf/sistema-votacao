package dev.luanfernandes.votacao.domain.service;

import dev.luanfernandes.votacao.api.exceptions.DateTimeException;
import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.domain.entity.ResultadoSessao;
import dev.luanfernandes.votacao.domain.entity.Sessao;

import java.util.List;

public interface SessaoService {
    Sessao abrir(Sessao sessao) throws DateTimeException, NotFoundException;

    boolean verificaSessaoComDataHoraMaiorIgualAtual(Sessao sessao);

    boolean verificaSessaoComDiferencaMinimaUmMinuto(Sessao sessao);

    Sessao obterPorId(Long id) throws NotFoundException;

    List<Sessao> obterTodos();

    ResultadoSessao obterResultadoPorIdSessao(Long idSessao) throws NotFoundException;
}
