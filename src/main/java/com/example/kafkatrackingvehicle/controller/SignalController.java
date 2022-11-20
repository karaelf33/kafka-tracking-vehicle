package com.example.kafkatrackingvehicle.controller;

import com.example.kafkatrackingvehicle.model.Vehicle;
import com.example.kafkatrackingvehicle.stream.ProducerCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/kafka")
public class SignalController {

    private final ProducerCreator producerCreator;

    public SignalController(ProducerCreator producerCreator) {
        this.producerCreator = producerCreator;
    }

    @PostMapping("/signals")
    public ResponseEntity<String> vehicleSignals(@RequestBody Vehicle vehicle) {
        producerCreator.sendMessage(vehicle);
        return ResponseEntity.ok("Message sent to kafka topic");
    }






  /*  KafkaProducerConfig producerConfig;

    public SignalController(KafkaProducerConfig producerConfig) {
        this.producerConfig = producerConfig;
    }

    @Scheduled(cron = "* * * * * *")
    public void getHeadValue() {
      int randomLocation = (int)((Math.random() * (99)) + 1);
        System.out.println(LocalDateTime.now());
        Set<Vehicle> locations=new HashSet<>();
       for (int i=0;i<20;i++)
       {
           Vehicle vehicle=new Vehicle(i,randomLocation,randomLocation);
           locations.add(vehicle);
       }
        Producer<String, Set<Vehicle>> producer = producerConfig.createProducer();
        ProducerRecord<String, Set<Vehicle>> record = null;
        record = new ProducerRecord<>(TOPIC, locations);
        producer.send(record);
        producer.flush();
        producer.close();
    }*/

}
