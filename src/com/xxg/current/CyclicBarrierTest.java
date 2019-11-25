package com.xxg.current;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//CyclicBarrier让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，
// 屏障才会开门，所有被屏障拦截的线程才会继续干活
public class CyclicBarrierTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        //构造方法参数 指的是屏障拦截的线程数量
        final CyclicBarrier cb = new CyclicBarrier(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        //getNumberWaiting方法可以获得CyclicBarrier阻塞的线程数量
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("线程" + Thread.currentThread().getName() + "即将达到集合地点1，当前已有" +
                                (cb.getNumberWaiting() + 1) + "个已到达，" + (cb.getNumberWaiting() == 2 ? "都到齐了，继续走啊" : "正在等待"));
                        cb.await();

                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("线程" + Thread.currentThread().getName() + "即将达到集合地点2，当前已有" +
                                (cb.getNumberWaiting() + 1) + "个已到达，" + (cb.getNumberWaiting() == 2 ? "都到齐了，继续走啊" : "正在等待"));
                        cb.await();

                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("线程" + Thread.currentThread().getName() + "即将达到集合地点3，当前已有" +
                                (cb.getNumberWaiting() + 1) + "个已到达，" + (cb.getNumberWaiting() == 2 ? "都到齐了，继续走啊" : "正在等待"));
                        cb.await();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            service.execute(runnable);
        }
        service.shutdown();
    }
}
