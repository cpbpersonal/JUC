package com.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExector {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2, //核心线程数，最小线程数
                5,//池中可运行的最大线程数
                3,  //线程活跃时间
                TimeUnit.SECONDS, //时间单位
                new ArrayBlockingQueue<>(3),//阻塞队列大小
                Executors.defaultThreadFactory(),  //线程工厂--默认
               new  ThreadPoolExecutor.DiscardOldestPolicy() );//拒绝策略//队列满了抛出异常
        try {
            for (int i = 0; i < 9; i++) {
                threadPoolExecutor.execute(()->{
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            threadPoolExecutor.shutdown();
        }
    }



}
