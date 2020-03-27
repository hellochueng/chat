package org.lzz.chat.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

//@Component
public class MQSender {

//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    final RabbitTemplate.ConfirmCallback confirmCallback= new RabbitTemplate.ConfirmCallback() {
//
//        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//            System.out.println("correlationData: " + correlationData);
//            System.out.println("ack: " + ack);
//            if(!ack){
//                System.out.println("异常处理....");
//            }
//        }
//
//    };
//
//    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
//
//        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//            System.out.println("return exchange: " + exchange + ", routingKey: "
//                    + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
//        }
//    };
//
//    //发送消息方法调用: 构建Message消息
//    @Transactional
//    public void send(Object message) throws Exception {
//        MessageProperties mp = new MessageProperties();
//        //在生产环境中这里不用Message，而是使用 fastJson 等工具将对象转换为 json 格式发送
//        Message msg = new Message(message.toString().getBytes(),mp);
//        rabbitTemplate.setMandatory(true);
//        rabbitTemplate.setConfirmCallback(confirmCallback);
//        rabbitTemplate.setReturnCallback(returnCallback);
//        //id + 时间戳 全局唯一
//        CorrelationData correlationData = new CorrelationData("1234567890"+new Date());
//        rabbitTemplate.convertAndSend("transaction", "key1", msg, correlationData);
//    }
}