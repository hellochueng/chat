package org.lzz.chat.test;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class test3 {

    static int i = 1;

    public static void main(String[] args) throws InterruptedException {
//        int[] s = new int[]{1,2};
//        String something = null;
//
//        System.out.println(
//                Optional.ofNullable(something).orElse("none")
//        );

//        System.out.println(3%10);
//        ExecutorService threadPool = Executors.newFixedThreadPool(Integer.MAX_VALUE);
//
//        CountDownLatch countDownLatch = new CountDownLatch(1000);
//
//
//        for (int x = 0;x < 1000; x++) {
//            threadPool.execute(()->{
//                i++;
//            });
//        }
//        System.out.println("zz");
//        Thread.sleep(2000);
//        System.out.println(i);
//        threadPool.shutdown();

//        go g = new go() {
//            @Override
//            public double sort(int a) {
//                return sqrt(a);
//            }
//        };
//        System.out.println(g.sort(1));
//        System.out.println(g.sort(1));


        List<String> names = Arrays.asList("peter", "anna", "aake", "xenia");
//        names.sort((a,b)-> a.compareTo(b));
//13530647233
//        names.forEach((a)-> System.out.println(a));
//
//        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
//        Integer converted = converter.convert("123");
//        System.out.println(converted); //class java.lang.Integer
//
//        Converter<String, Integer> converter1 = Integer::valueOf;
//        Integer converted2 = converter.convert("321");
//        System.out.println(converted2);

//        Something something2 = new Something();
//        Converter<String, String> converter = something2::startsWith;
//        String converted = converter.convert("赖章洲");
//        System.out.println(converted);    // "J"


        int num = 1;
//        Converter<Integer, String> stringConverter =
//                (from) -> String.valueOf(from + num);
        num = 3;//在lambda表达式中试图修改num同样是不允许的。
    }

    static class Something {
        String startsWith(String s) {
            return String.valueOf(s.charAt(0));
        }
    }

    interface Converter<F, T> {
        T convert(F from);
    }

    static class dosomething implements Runnable{
        CountDownLatch countDownLatch;
        public dosomething(CountDownLatch countDownLatch){
            this.countDownLatch=countDownLatch;
        }
        @Override
        public void run() {
            i=i+1;
            System.out.println(i);
        }
    }

    interface go{
        double sort(int a);

        default double sqrt(int a) {
            return Math.sqrt(a);
        }
    }

}
