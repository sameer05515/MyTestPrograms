package com.prem.kafka.producer.rest;

import com.prem.kafka.producer.service.KafkaProducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    private final KafkaProducerService producerService;

    public KafkaController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/message")
    public void sendMessage(@RequestBody String message) {
        producerService.sendMessage(message);
    }
}
