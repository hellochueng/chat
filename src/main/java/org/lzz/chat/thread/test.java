package org.lzz.chat.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1. 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        List<Future<Integer>> list=new ArrayList<Future<Integer>>();

        for (int i = 0; i < 10; i++) {
            Future<Integer> future=pool.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    System.out.println(Thread.currentThread().getName());
                    int sum=0;
                    for (int i = 0; i <=100; i++) {
                        sum+=i;
                    }
                    return sum;
                }
            });
            list.add(future);
        }

        pool.shutdown();

        for (Future<Integer> future : list) {
            System.out.println(future.get());
        }
    }
}
