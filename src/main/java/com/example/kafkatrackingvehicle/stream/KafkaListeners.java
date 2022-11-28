package com.example.kafkatrackingvehicle.stream;

import com.example.kafkatrackingvehicle.model.Vehicle;
import com.example.kafkatrackingvehicle.util.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.example.kafkatrackingvehicle.constant.Constant.GROUP_ID;
import static com.example.kafkatrackingvehicle.constant.Constant.TOPIC;

@Component
public class KafkaListeners {
    private static final Logger log = LoggerFactory.getLogger(KafkaListeners.class);


    @KafkaListener(topics = TOPIC, groupId = GROUP_ID,containerFactory = "kafkaListenerContainerFactory")
    public void consume(Vehicle vehicle) {
        Double distance= Calculator.calculateDistance(vehicle);
        log.info("Vehicle Distance: {}",distance);
    }

}
