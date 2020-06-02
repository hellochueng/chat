package org.lzz.chat.rabbit;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class MQReceiver {

    @RabbitListener(bindings ={
                    @QueueBinding(value = @Queue(value = "key1",durable = "true")
                    ,exchange =@Exchange(value = "transaction",durable = "true")
                    ,key = "key1")})
    public void onMessage(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag,true);
        System.out.println("receive--1: " + new String(message.getBody()));
    }
}