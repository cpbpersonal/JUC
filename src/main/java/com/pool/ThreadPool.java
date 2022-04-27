package com.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
//三大方法
public class ThreadPool {
    public static void main(String[] args) {
        ExecutorService SingleThreadExecutor = Executors.newSingleThreadExecutor();//单个线程
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);//固定线程
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();//动态线程数量

        try {
            for (int i = 0; i < 100; i++) {
                //SingleThreadExecutor.execute(()->{
                //fixedThreadPool.execute(()->{
                    cachedThreadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //SingleThreadExecutor.shutdown();
            //fixedThreadPool.shutdown();
            cachedThreadPool.shutdown();
        }
    }
}
