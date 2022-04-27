package com.util;

import java.util.concurrent.CountDownLatch;

public class countdown {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);//创建一个对象，初始化数量

            for (int i = 0; i < 6; i++) {
                new Thread(()->{
                System.out.println("线程执行");
                countDownLatch.countDown();//每个线程每次调用减一
            }).start();
        }
        countDownLatch.await();//等待计数器归零
        System.out.println("关门了");
    }
}
