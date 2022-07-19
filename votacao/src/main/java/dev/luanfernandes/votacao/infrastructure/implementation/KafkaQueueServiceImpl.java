package dev.luanfernandes.votacao.infrastructure.implementation;

import dev.luanfernandes.votacao.core.kafka.KafkaConfig;
import dev.luanfernandes.votacao.core.kafka.TopicProperties;
import dev.luanfernandes.votacao.domain.listener.KafkaQueueService;
import dev.luanfernandes.votacao.domain.listener.event.VotacaoEncerradaEvent;
import dev.luanfernandes.votacao.domain.records.SessaoVotacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


@Profile("!test")
@Service
@Slf4j
public class KafkaQueueServiceImpl implements KafkaQueueService {
    private final TopicProperties topicProperties;
    private final KafkaConfig kafkaConfig;

    public KafkaQueueServiceImpl(TopicProperties topicProperties, KafkaConfig kafkaConfig) {
        this.topicProperties = topicProperties;
        this.kafkaConfig = kafkaConfig;
    }

    @EventListener
    @Override
    public void votacaoEncerradaListener(VotacaoEncerradaEvent event) {
        log.info("Votação {} encerrada. Notificando demais sistemas.", event.getSessaoVotacao());
        send(event.getSessaoVotacao());
    }



    @Override
    public void send(SessaoVotacao sessaoVotacao) {
        kafkaConfig.kafkaTemplate().send(topicProperties.getName(),sessaoVotacao);
    }

}
