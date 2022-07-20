package dev.luanfernandes.consumer.config;


import dev.luanfernandes.consumer.record$.SessaoVotacao;
import lombok.Getter;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;

@Getter
@Configuration
public class KafkaConfig {
    private final KafkaProperties kafkaProperties;

    public KafkaConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        return  kafkaProperties.buildConsumerProperties();
    }

    @Bean
    public ConsumerFactory<String, SessaoVotacao> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

}