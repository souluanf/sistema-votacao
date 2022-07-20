package dev.luanfernandes.votacao.domain.service;


import dev.luanfernandes.votacao.domain.record$.SessaoVotacao;

public interface PublisherService {
    void sessaoFinalizada(SessaoVotacao sessaoVotacao);
}
