package com.example.kafkatrackingvehicle.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

import static com.example.kafkatrackingvehicle.constant.Constant.*;

@Component
public class KafkaListeners {
    private static final Logger log = LoggerFactory.getLogger(KafkaListeners.class);


    @KafkaListener(topics = TOPIC,groupId = GROUP_ID)
    public void consume(String message) {
        CountDownLatch latch = new CountDownLatch(1);
        Runnable consumerRunnable = new ConsumerRunnable(
                BOOTSTRAP_SERVERS,
                TOPIC,
                latch
        );
        Thread thread = new Thread(consumerRunnable);
        thread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Caught shutdown hook");
            ((ConsumerRunnable)consumerRunnable).shutdown();
            try {
                latch.await();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            finally {
                log.info("App is closed");
            }
            log.info("Application has exited");
        }));
        log.info(String.format("Message received ->{}", message));
    }
}
