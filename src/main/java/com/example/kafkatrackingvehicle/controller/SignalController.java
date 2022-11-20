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


}
