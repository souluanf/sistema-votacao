package dev.luanfernandes.votacao.domain.listener;

import dev.luanfernandes.votacao.domain.listener.event.VotacaoEncerradaEvent;
import dev.luanfernandes.votacao.domain.record$.SessaoVotacao;


public interface KafkaQueueService {

    void votacaoEncerradaListener(VotacaoEncerradaEvent event);

    void send(SessaoVotacao sessaoVotacao);
}
