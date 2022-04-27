package com.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class FutureSupply {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //有返回值
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->{
            int i=1/0;
            System.out.println(Thread.currentThread().getName()+"=>String");
            return "1024";
        });

        System.out.println(completableFuture.whenComplete((t, u) -> {
            System.out.println("t=>"+t);//正常结果
            System.out.println("u=>"+u);//异常结果
        }).exceptionally((e) -> {
            //输出异常信息
            System.out.println(e.getMessage());
            return "失败了";
        }).get());
    }
}
