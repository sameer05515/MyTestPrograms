package com.prem.kafka.producer.controller;

// import com.example.service.KafkaProducerService;
import com.prem.kafka.producer.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

  @Autowired private KafkaProducerService producerService;

  @GetMapping("/send")
  public String sendMessage(@RequestParam String message) {
    producerService.sendMessage(message);
    return "Sent: " + message;
  }
}
