package com.xxg.current;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//CountDownLatch它允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行
public class CountdownLatchTest {
    public static void main(String[] args) {

        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdAnswer = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("运动员" + Thread.currentThread().getName() + "等待鸣枪");
                        //调用await方法会当前线程挂起，直到count值为0才继续执行
                        cdOrder.await();

                        System.out.println("运动员" + Thread.currentThread().getName() + "开跑");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("运动员" + Thread.currentThread().getName() + "到达终点");
                        //将count值减1 相当于告知各自线程已经运行结束
                        cdAnswer.countDown();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            service.execute(runnable);
        }

        try {
            Thread.sleep(1000);
            System.out.println("裁判" + Thread.currentThread().getName() + "即将开始鸣枪");
            cdOrder.countDown();
            System.out.println("裁判" + Thread.currentThread().getName() + "已经鸣枪,等待运动员跑远");
            cdAnswer.await();
            System.out.println("三个运动员跑到终点，裁判" + Thread.currentThread().getName() + "统计名次");

        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
}
