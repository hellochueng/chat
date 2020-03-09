package org.lzz.chat.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.lzz.chat.app;
import org.lzz.chat.domain.User;
import org.lzz.chat.mapper.UserMapper;
import org.lzz.chat.rabbit.MQSender;
import org.lzz.chat.redis.RedisDistributedLock;
import org.lzz.chat.util.RandomValue;
import org.lzz.chat.util.SnowFlakeGenerator;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = app.class) // 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration
public class TestDemo {

    @Autowired
    RedisDistributedLock redisDistributedLock;

    @Autowired private UserMapper userMapper;
    @Autowired private SnowFlakeGenerator snowFlakeGenerator;

    @Autowired private MQSender mqSender;

    @Test
    public void rabbitTest() throws Exception {
        String msg = "hello spring boot";
        Message message = new Message(msg.getBytes(),new MessageProperties());
        try {
            for (int i = 0; i < 15; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mqSender.send(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() throws ExecutionException, InterruptedException {

        ExecutorService executors = Executors.newFixedThreadPool(50);
        Integer a=0;
        for(int i =0;i<50;i++) {
            a += executors.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    for (int j = 0; j < 5; j++) {
                        User user = new User(snowFlakeGenerator.generateLongId(), RandomValue.getTel(), "123456", RandomValue.getChineseName(), RandomValue.getEmail(8, 20), RandomValue.name_sex);
                        userMapper.insertUser(user);
                        System.out.println(Thread.currentThread().getName()+j);
                    }
                    return 1;
                }
            }).get();
        }
        System.out.println("-----------------------------"+a);
    }

    @Test
    public void test1() throws InterruptedException {
        while(redisDistributedLock.setLock("lock1",20000)){
            System.out.println("1-----------进入锁");
            Thread.sleep(10000);
        }
        redisDistributedLock.releaseLock("lock1",redisDistributedLock.get("lock1"));
        System.out.println("1-----------释放锁");
    }

    @Test
    public void test2() throws InterruptedException {
        while(redisDistributedLock.setLock("lock1",20000)){
            System.out.println("2--------进入锁");
            Thread.sleep(10000);
        }
        redisDistributedLock.releaseLock("lock1",redisDistributedLock.get("lock1"));
        System.out.println("2------------释放锁");
    }
}