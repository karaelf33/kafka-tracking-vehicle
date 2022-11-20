package com.example.kafkatrackingvehicle.stream;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import static com.example.kafkatrackingvehicle.constant.Constant.*;


public class ConsumerThread implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(ConsumerThread.class.getName());
    private final CountDownLatch latch;
    private final KafkaConsumer<String, String> consumer;

    public ConsumerThread(String bootstrapServers,
                          String groupId,
                          String topic,
                          CountDownLatch latch) {
        this.latch = latch;
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Collections.singleton(TOPIC));
    }

    @Override
    public void run() {
       try {
           while (true) {
               ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
               for (ConsumerRecord<String, String> record : records) {
                   log.info("Key: " + record.key() + ", Value:" + record.value());
                   log.info("Partition: " + record.partition() + ", Offset:" + record.offset());
               }
           }
       }catch (WakeupException e){
           log.info("Received shutdown signal!");
       }finally {
           consumer.close();
           latch.countDown();
       }
    }

    public void shutdown() {
        consumer.wakeup();
    }
}
