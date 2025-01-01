package com.adilAlouan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String,Object> template;

    public void sendMessage(String topic, String message) {
        CompletableFuture<SendResult<String, Object>> future = template.send(topic, message);
        future.whenComplete((r, e) -> {
            if (e == null) {
                System.out.println("Sent message= [" + message + "] with offset= [" + r.getRecordMetadata().offset() + "]");
            }else {
                System.out.println("Unable to send message= [" + message + "] due to error= [" + e.getMessage() + "]");
            }
        });
    }
}
