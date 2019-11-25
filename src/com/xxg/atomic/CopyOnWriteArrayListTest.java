package com.xxg.atomic;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class CopyOnWriteArrayListTest {
    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        copyOnWriteArrayList.add("hello");
        copyOnWriteArrayList.add("world");
        copyOnWriteArrayList.add("2019");
        copyOnWriteArrayList.add("just do it");
        copyOnWriteArrayList.add("right now");

        //iterator方法存在弱一致性，底层数组增删改进行数组的替换后，并没有将
        //原先的数组引用进行替换，无法拿到最新数据
        Iterator iterator = copyOnWriteArrayList.iterator();
        new Thread(() -> {
             copyOnWriteArrayList.remove(1);
             copyOnWriteArrayList.remove(3);
        }).start();
        TimeUnit.SECONDS.sleep(3);

        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
