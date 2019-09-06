package org.lzz.chat.test;


import org.lzz.chat.app;
import org.lzz.chat.redis.RedisDistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = app.class) // 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration
public class TestDemo {

    @Autowired
    RedisDistributedLock redisDistributedLock;

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