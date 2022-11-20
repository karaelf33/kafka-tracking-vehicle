package com.example.kafkatrackingvehicle;

import com.example.kafkatrackingvehicle.constant.Constant;
import com.example.kafkatrackingvehicle.stream.ConsumerCreator;
import com.example.kafkatrackingvehicle.stream.ProducerCreator;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaTrackingVehicleApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaTrackingVehicleApplication.class, args);
        ProducerCreator.runProducer();
    }

}
