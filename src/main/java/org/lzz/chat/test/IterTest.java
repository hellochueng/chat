package org.lzz.chat.test;

import java.util.Arrays;
import java.util.Iterator;

public class IterTest implements Iterable<String> {
    private Object[] obj=new Object[1];
    //记录添加元素的个数
    private int size;
    private int current=0;
    //添加元素
    public void add(String str){
        if(size==obj.length){
            //扩张数组到一个新长度
            obj= Arrays.copyOf(obj, obj.length+obj.length<<1);
        }
        obj[size++]=str;
    }
    public Iterator<String> iterator() {
        class iter implements Iterator<String> {
            @Override
            public boolean hasNext() {
                // 判断当前指针是否小于实际大小
                if(current<size){
                    return true;
                }
                return false;
            }
 
            @Override
            public String next() {
                // 返回当前元素，并把当前下标前移
                return obj[current++].toString();
            }
 
            @Override
            public void remove() {
                // TODO Auto-generated method stub
                 
            }
        }
        return new iter();
    }

    public static void main(String[] args) {
        IterTest s = new IterTest();
        for (Object o : s) {

        }
    }
}