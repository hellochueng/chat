package org.lzz.chat.test;

import java.io.IOException;

public class Main {

    public static void startTest() {
        int a=127;
        char b=65535;
        short d=32767;
        double c = '中';
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(a==b);
//        try (MyAutoCloseA a = new MyAutoCloseA();
//            MyAutoCloseB b = new MyAutoCloseB();) {
//
//            a.test();
//            b.test();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            Throwable[] suppressed = e.getSuppressed();
//            for (int i = 0; i < suppressed.length; i++)
//                System.out.println(suppressed[i].getMessage());
//        }
    }

    public static void main(String[] args) throws Exception {
        startTest();
    }
}

class MyAutoCloseA implements AutoCloseable {

    public void test() throws IOException {
        System.out.println("A: test");
        throw new IOException("A: test() IOException");
    }

    @Override
    public void close() throws Exception {
        System.out.println("A: close");
//        throw new ClassNotFoundException("A: close() ClassNotFoundException");
    }
}

class MyAutoCloseB implements AutoCloseable {

    public void test() throws IOException {
        System.out.println("B: test");
        throw new IOException("B: test() IOException");
    }

    @Override
    public void close() throws Exception {
        System.out.println("B: close");
        throw new ClassNotFoundException("B: close() ClassNotFoundException");
    }
}