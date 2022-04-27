package com.future;

import com.pool.ThreadPool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Future {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //runAsync无返回值异步回调
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"=>void回调");
        });

        System.out.println(1111);//继续向下执行先返回
        voidCompletableFuture.get();//回调获取阻塞的执行结果
    }
}
