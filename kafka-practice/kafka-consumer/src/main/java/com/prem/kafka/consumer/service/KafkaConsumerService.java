package com.prem.kafka.consumer.service;

import java.util.function.Function;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

  Function<String, String> transform = s -> s.toUpperCase();

  @KafkaListener(topics = "test-topic", groupId = "my-group")
  public void consume(String message) {
    System.out.println("Consumed message: " + transform.apply(message));
  }
}
