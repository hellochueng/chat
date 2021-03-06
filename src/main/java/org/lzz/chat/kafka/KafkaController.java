package org.lzz.chat.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/kafka")
    public String testKafka() {
        int iMax = 10;
        for (int i = 1; i < iMax; i++) {
            kafkaTemplate.send("test","key" + i, "data" + i);
        }
        return "success";
    }

    @GetMapping("/kafka/student")
    public String student() {
        int iMax = 10;
        for (int i = 1; i < iMax; i++) {
            kafkaTemplate.send("metrics-group","key" + i, "data" + i);
        }
        return "success";
    }

    @KafkaListener(topics = "test")
    public void receive(ConsumerRecord<?, ?> consumer) {
        System.out.println("{"+consumer.topic()+"} - {"+consumer.key()+"}:{"+consumer.value()+"}");
    }
}