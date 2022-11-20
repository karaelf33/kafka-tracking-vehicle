package com.example.kafkatrackingvehicle;

import com.example.kafkatrackingvehicle.stream.ProducerCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaTrackingVehicleApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaTrackingVehicleApplication.class, args);
        ProducerCreator.runProducer();
    }

}
