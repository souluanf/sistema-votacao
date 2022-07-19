package dev.luanfernandes.votacao.core.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("topic")
public class TopicProperties {
    private String name;
    private  Integer messagesPerRequest;
    private  Integer partitions;
    private short replicationFactory;
}
