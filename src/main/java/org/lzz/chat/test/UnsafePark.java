package org.lzz.chat.test;

import sun.misc.Unsafe;

import java.lang.reflect.Field;


public class UnsafePark {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafes = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafes.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafes.get(Unsafe.class);
        unsafe.park(false,0l);
    }
}
