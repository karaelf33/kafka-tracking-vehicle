package com.example.kafkatrackingvehicle;


import com.example.kafkatrackingvehicle.model.Vehicle;
import com.example.kafkatrackingvehicle.util.JsonSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.kafkatrackingvehicle.constant.Constant.BOOTSTRAP_SERVERS;
import static com.example.kafkatrackingvehicle.constant.Constant.TOPIC;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class KafkaTestContainerTest {
    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));

    @Test
    public void simpleTest() throws InterruptedException {
        int locX= ThreadLocalRandom.current().nextInt(1, 100 + 1);
        int locY= ThreadLocalRandom.current().nextInt(1, 100 + 1);
        int id= ThreadLocalRandom.current().nextInt(1, 100 + 1);
        //creating producer and consumers
        Producer<String, Vehicle> producer = createProducer();
        ConsumerRunnable consumer = createConsumer();
        ProducerRecord<String, Vehicle> record = new ProducerRecord<>(TOPIC, "id_"+id, new Vehicle(id,locX,locY));

        producer.send(record);
        //starting consumer
        Thread thread = new Thread(consumer);
        thread.start();
        await().atMost(10, SECONDS).until(() -> consumer.getRecordHistory().size() > 0);
        assertEquals(1, consumer.getRecordHistory().size());

    }

    private Producer<String, Vehicle> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        return new KafkaProducer<>(props);
    }

    private ConsumerRunnable createConsumer() {
        Properties props = new Properties();
        CountDownLatch latch = new CountDownLatch(1);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, Vehicle> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(List.of("vehicle_tracking_02"));
        return new ConsumerRunnable(BOOTSTRAP_SERVERS,latch);
    }
}
