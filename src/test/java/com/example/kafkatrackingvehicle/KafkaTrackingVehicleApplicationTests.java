package com.example.kafkatrackingvehicle;

import com.example.kafkatrackingvehicle.model.Vehicle;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;


@SpringBootTest
class KafkaTrackingVehicleApplicationTests extends AbstractIntegrationTest {
    @Autowired
    KafkaProperties properties;

    @Test
    void contextLoads() {

        String topicName = "vehicle_tracking_02";
        final Consumer<String, String> consumer = createConsumer(topicName);
        final ConsumerRecords<String, String> records = KafkaTestUtils.getRecords(consumer, 5000);
        final ArrayList<String> actualValues = new ArrayList<>();
        final ArrayList<String> ours = new ArrayList<>();
        int locX= ThreadLocalRandom.current().nextInt(1, 100 + 1);
        int locY= ThreadLocalRandom.current().nextInt(1, 100 + 1);
        LongStream.range(0, 10).forEach(i -> ours.add(String.valueOf(new Vehicle((int) i,locX,locY))));
        records.forEach(record -> actualValues.add(record.value()));
        assertEquals(ours, actualValues);
    }

    private Consumer<String, String> createConsumer(final String topicName) {
        final Map<String, Object> props=KafkaTestUtils.consumerProps("localhost:9092","testApp","true");
        final Consumer<String,String>
                consumer=
                new DefaultKafkaConsumerFactory<>(props, StringDeserializer::new,StringDeserializer::new).createConsumer();
        consumer.subscribe(Collections.singletonList(topicName));
        return null;
    }

}
