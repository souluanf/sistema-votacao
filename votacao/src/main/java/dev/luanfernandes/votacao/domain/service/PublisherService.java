package dev.luanfernandes.votacao.domain.service;

import dev.luanfernandes.votacao.domain.records.SessaoVotacao;

public interface PublisherService {
    void sessaoFinalizada(SessaoVotacao sessaoVotacao);
}
