package org.lzz.chat.controller;

import org.lzz.chat.entity.Result;
import org.lzz.chat.redis.RedisDistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequestMapping("lock")
@RestController
public class DistrubutedLockController {

    @Autowired RedisDistributedLock redisDistributedLock;
    private Lock lock = new ReentrantLock();
    @RequestMapping("lock/{id}")
    public Result lock1(@PathVariable String id) throws InterruptedException {
        System.out.println(Thread.currentThread().getId()+"-----------等待锁");
        //获取锁，返回true 进入循环 获取成功
        redisDistributedLock.setLock("lock1",5000);
        System.out.println(Thread.currentThread().getId()+"-----------开始任务");
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getId()+"-----------结束任务");
        redisDistributedLock.releaseLock("lock1",redisDistributedLock.get("lock1"));
        return Result.success();
    }
}
