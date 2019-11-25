package com.xxg.current;

import java.util.concurrent.Semaphore;

//Semaphore可以控同时访问的线程个数，通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
public class SemaphoreTest {
    public static void main(String[] args) {
        int N = 8;
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < N; i++) {
            new Worker(i, semaphore).start();
        }
    }

    static class Worker extends Thread {
        private int num;
        private Semaphore semaphore;

        public Worker(int num, Semaphore Semaphore) {
            this.num = num;
            this.semaphore = Semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("工人: " + this.num + "占用一个机器在生成...");
                Thread.sleep(2000);
                System.out.println("工人: " + this.num + "释放出机器...");
                semaphore.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
