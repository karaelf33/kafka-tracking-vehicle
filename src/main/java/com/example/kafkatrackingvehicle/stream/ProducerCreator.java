package com.example.kafkatrackingvehicle.stream;

import com.example.kafkatrackingvehicle.model.Vehicle;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static com.example.kafkatrackingvehicle.constant.Constant.BOOTSTRAP_SERVERS;
import static com.example.kafkatrackingvehicle.constant.Constant.TOPIC;


@Configuration
public class ProducerCreator {

    private static final Logger log = LoggerFactory.getLogger(ProducerCreator.class);

    @Autowired
    private KafkaTemplate<String, Vehicle> kafkaTemplate;

    public void sendMessage(Vehicle vehicle) {
        log.info(String.format("Message sent -> %s", vehicle));
        kafkaTemplate.send(TOPIC, vehicle);
    }

    @Bean
    public static Producer<String, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        return new KafkaProducer<>(props);
    }

    @Bean
    public static void runProducer() {
        Producer<String, String> producer = ProducerCreator.createProducer();

        for (int i = 0; i < 10; i++) {
            String key ="id_" + Integer.toString(i);
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, key, "This is record " + i);
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {

                    if (e == null) {
                        System.out.println("bbbbbbb");
                        log.info("Received new metadata. \n" +
                                "Topic :" + recordMetadata.topic() + "\n" +
                                "Partition:" + recordMetadata.partition() + "\n" +
                                "Offset: " + recordMetadata.offset() + "\n" +
                                "Timestamp" + recordMetadata.timestamp());
                    } else {
                        log.error("Error while producing ", e);
                    }
                }
            });

        }

        producer.flush();
        producer.close();
    }
}
