package com.example.kafkatrackingvehicle.stream;

import com.example.kafkatrackingvehicle.model.Vehicle;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import static com.example.kafkatrackingvehicle.constant.Constant.TOPIC;



public class ConsumerRunnable implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(ConsumerRunnable.class.getName());
    private final CountDownLatch latch;
    private final KafkaConsumer<String, Vehicle> consumer;


    public ConsumerRunnable(String bootstrapServers,
                            String topic,
                            CountDownLatch latch) {
        this.latch = latch;
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
       props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new KafkaConsumer<>(props);
        TopicPartition partitionToReadFrom=new TopicPartition(TOPIC,0);
        long offsetReadFrom=15L;
        consumer.assign(List.of(partitionToReadFrom));


        consumer.seek(partitionToReadFrom,offsetReadFrom);


    }

    @Override
    public void run() {
        try {
            int message=0;
            while (message<10) {
                message++;
                ConsumerRecords<String, Vehicle> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, Vehicle> RECORD : records) {
                    log.info("Calculate distance, Key: {}, Value: {}" , RECORD.key() , RECORD.value());
                    log.info("Partition: {}, Offset:{}" , RECORD.partition() , RECORD.offset());
                }
            }
        }catch (WakeupException e) {
            log.info("Received shutdown signal!");
        } finally {
            consumer.close();
            latch.countDown();
        }
    }

    public void shutdown() {
        consumer.wakeup();
    }


}
