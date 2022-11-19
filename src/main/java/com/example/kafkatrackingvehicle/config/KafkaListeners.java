package com.example.kafkatrackingvehicle.config;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "tracking_vehicle",
            groupId = "Yusuf_Groupd_id")
    void listener(String data) {
        System.out.println("Listener received:" + data + "sjkd");
    }
}
