package dev.luanfernandes.votacao.infrastructure.implementation;


import dev.luanfernandes.votacao.domain.listener.event.VotacaoEncerradaEvent;
import dev.luanfernandes.votacao.domain.record$.SessaoVotacao;
import dev.luanfernandes.votacao.domain.service.PublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PublisherServiceImpl implements PublisherService {

    private final ApplicationEventPublisher eventPublisher;

    public PublisherServiceImpl(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    @Override
    public void sessaoFinalizada(SessaoVotacao sessaoVotacao) {
        eventPublisher.publishEvent(new VotacaoEncerradaEvent(sessaoVotacao));
    }
}