package dev.luanfernandes.votacao.core.kafka;


import dev.luanfernandes.votacao.domain.record$.SessaoVotacao;
import lombok.Getter;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Profile("!test")
@Getter
@Configuration
@EnableKafka
public class KafkaConfig {

    private final KafkaProperties properties;
    private final TopicProperties config;

    public KafkaConfig(KafkaProperties properties, TopicProperties config) {
        this.properties = properties;
        this.config = config;
    }

    @Profile("!test")
    @Bean
    public KafkaTemplate<String, SessaoVotacao> kafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(properties.buildProducerProperties()));
    }

    @Profile("!test")
    @Bean
    public NewTopic createTopic() {
        return TopicBuilder
                .name(config.getName())
                .partitions(config.getPartitions())
                .replicas(config.getReplicationFactory())
                .build();
    }
}