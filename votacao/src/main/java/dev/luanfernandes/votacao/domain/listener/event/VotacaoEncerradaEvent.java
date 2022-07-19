package dev.luanfernandes.votacao.domain.listener.event;


import dev.luanfernandes.votacao.domain.records.SessaoVotacao;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class VotacaoEncerradaEvent {
    private SessaoVotacao sessaoVotacao;
}
