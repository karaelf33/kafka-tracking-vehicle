package com.example.kafkatrackingvehicle.stream;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;


public class ConsumerRunnable implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(ConsumerRunnable.class.getName());
    private final CountDownLatch latch;
    private KafkaConsumer<String, String> consumer;

    public ConsumerRunnable(String bootstrapServers,
                            String groupId,
                            String topic,
                            CountDownLatch latch) {
        this.latch = latch;
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singleton(topic));
        try {
            int i=0;
            while (true) {
                i++;
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> RECORD : records) {
                    System.out.println("AAAAAAAAAAA");
                    log.info("Key: {}" , RECORD.key() , ", Value: {}" , RECORD.value());
                    log.info("Partition: {}" , RECORD.partition() , ", Offset:{}" , RECORD.offset());
                }
            }
        }catch (WakeupException e){
            log.info("Received shutdown signal!");
        }finally {
            consumer.close();
            latch.countDown();
        }
    }

    @Override
    public void run() {

    }

    public void shutdown() {
        consumer.wakeup();
    }
}
