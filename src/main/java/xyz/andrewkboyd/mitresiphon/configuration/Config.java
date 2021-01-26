package xyz.andrewkboyd.mitresiphon.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {


    @Bean
    NewTopic createNVDCVEModifiedTopic(){
        return TopicBuilder.name("nvd.cve.modified")
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic createNVDCVERecentTopic(){
        return TopicBuilder.name("nvd.cve.recent")
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic createNVDCVECompleteTopic(){
        return TopicBuilder.name("nvd.cve.complete")
                .replicas(1)
                .build();
    }
}
