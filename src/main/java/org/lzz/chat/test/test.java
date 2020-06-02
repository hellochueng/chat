package org.lzz.chat.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;

public class test {

    public static AtomicInteger i = new AtomicInteger(0);


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CountDownLatch a = new CountDownLatch(1000);
        ExecutorService es = Executors.newCachedThreadPool();
        List<Future<Integer>> results = new ArrayList<Future<Integer>>();
        Date now = new Date();
        for(int x =0;x<1000;x++) {
            results.add(es.submit(new Task(a)));
            a.countDown();
        }

        for(Future<Integer> res : results)
            if(res.get()==1000)
                System.out.println("结束:"+(new Date().getTime() - now.getTime()));

        es.shutdown();
        System.out.println("last"+i);
    }

    static class Task implements Callable<Integer> {

        CountDownLatch countDownLatch =null;
        public Task(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public Integer call() throws Exception {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int x = i.incrementAndGet();
            System.out.println(x);
            return x;
        }
    }
}
