package dev.luanfernandes.consumer.service;


import dev.luanfernandes.votacao.domain.records.SessaoVotacao;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ConsumerImpl implements Consumer {

    @KafkaListener(topics = {"votacao-encerrada"})
    @Override
    public void consume(ConsumerRecord<String, SessaoVotacao> rec) {
        log.info("Consumed message -> {} | topic {}, partition {}", rec.value(),rec.topic(),rec.partition());
        log.info("data {}",rec.value());
    }
}