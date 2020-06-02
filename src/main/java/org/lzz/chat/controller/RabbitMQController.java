package org.lzz.chat.controller;

import org.lzz.chat.rabbit.MQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {

    @Autowired private MQSender mqSender;

    @GetMapping("/send")
    public void send() throws Exception {
        mqSender.send("312");
    }
}
