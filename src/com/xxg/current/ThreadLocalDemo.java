package com.xxg.current;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalDemo {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            final Thread t = new Thread() {
                @Override
                public void run() {
                    System.out.println("当前线程： " + Thread.currentThread().getName() + ",已分配ID:" + ThreadId.get());
                }
            };
            t.start();
        }
    }

    static class ThreadId {
        private static final AtomicInteger nextId = new AtomicInteger(0);

        //线程本地变量，为每个线程关联一个唯一的序号
        private static final ThreadLocal<Integer> threadId =
                new ThreadLocal<Integer>() {
                    @Override
                    protected Integer initialValue() {
                        return nextId.getAndIncrement();//相当于nextId++,由于nextId++这种操作是个复合操作而非原子操作，会有线程安全问题(可能在初始化时就获取到相同的ID，所以使用原子变量
                    }
                };

        public static int get() {
            return threadId.get();
        }
    }
}
