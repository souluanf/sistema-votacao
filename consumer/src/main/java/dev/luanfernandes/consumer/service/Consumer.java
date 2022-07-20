package dev.luanfernandes.consumer.service;

import dev.luanfernandes.consumer.record$.SessaoVotacao;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface Consumer {
    void consume(ConsumerRecord<String, SessaoVotacao> rec);
}
