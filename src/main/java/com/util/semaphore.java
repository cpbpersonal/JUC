package com.util;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class semaphore {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 9; i++) {
            new Thread(()->{
                try {//
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"获得车位");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName()+"离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }

            },String.valueOf(i)).start();
        }
    }
}
