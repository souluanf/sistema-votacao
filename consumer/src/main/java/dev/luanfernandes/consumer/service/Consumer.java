package dev.luanfernandes.consumer.service;

import dev.luanfernandes.votacao.domain.records.SessaoVotacao;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface Consumer {
    void consume(ConsumerRecord<String, SessaoVotacao> rec);
}
