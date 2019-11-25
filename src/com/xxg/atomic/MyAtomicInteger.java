package com.xxg.atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class MyAtomicInteger {

    private volatile int value;
    private static long offset;   //偏移地址
    private static Unsafe unsafe;

    static {
        try {
            Field theUnsafeFiled = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeFiled.setAccessible(true);
            unsafe = (Unsafe)theUnsafeFiled.get(null);
            Field field = MyAtomicInteger.class.getDeclaredField("value");
            offset = unsafe.objectFieldOffset(field);   //获取偏移地址
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //先将A值以及地址的偏移量传递底层，判断要修改的字段在实例的哪个位置，这样就可以确定要进行修改字段的
    //底层通过比较该位置的值是否等于预期值，如果是，进行修改；如果不是，不进行修改
    public void increment(int num){
        int tempValue;
        do{
            tempValue = unsafe.getIntVolatile(this,offset);  //获取值
        }while (!unsafe.compareAndSwapInt(this,offset,tempValue,value+num));  //CAS自旋锁
    }

    public int get(){
        return value;
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[20];
        MyAtomicInteger myAtomicInteger = new MyAtomicInteger();
        for(int i=0; i<20; i++){
            threads[i] = new Thread(() -> {
                for (int j=0; j<1000; j++){
                    myAtomicInteger.increment(1);
                }
            });
            threads[i].start();
        }

        for(int i=0; i<threads.length; i++){
            try {
                threads[i].join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("x=" + myAtomicInteger.get());
    }
}
