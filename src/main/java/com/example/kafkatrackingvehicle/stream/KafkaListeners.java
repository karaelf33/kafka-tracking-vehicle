package com.example.kafkatrackingvehicle.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.example.kafkatrackingvehicle.constant.Constant.GROUP_ID;
import static com.example.kafkatrackingvehicle.constant.Constant.TOPIC;

@Component
public class KafkaListeners {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListeners.class);


    @KafkaListener(topics = TOPIC,
            groupId =GROUP_ID)
    public void consume(String message){
        LOGGER.info(String.format("Message received -> %s", message));
    }
}
