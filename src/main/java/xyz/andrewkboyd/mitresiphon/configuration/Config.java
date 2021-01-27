package xyz.andrewkboyd.mitresiphon.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import xyz.andrewkboyd.mitresiphon.tasks.NVDFetchTask;


@Configuration
public class Config {


    @Bean
    NewTopic createNVDCVEModifiedTopic(){
        return TopicBuilder.name(NVDFetchTask.MODIFIED_KAFKA_TOPIC)
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic createNVDCVERecentTopic(){
        return TopicBuilder.name(NVDFetchTask.RECENT_KAFKA_TOPIC)
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic createNVDCVECompleteTopic(){
        return TopicBuilder.name(NVDFetchTask.COMPLETE_KAFKA_TOPIC)
                .replicas(1)
                .build();
    }
}
