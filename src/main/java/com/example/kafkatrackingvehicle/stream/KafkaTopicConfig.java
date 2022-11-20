package com.example.kafkatrackingvehicle.stream;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.example.kafkatrackingvehicle.constant.Constant.TOPIC;


@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topicBuilder(){
        return TopicBuilder.name(TOPIC)
                .build();
    }
}