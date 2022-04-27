package com.voliate;

import java.util.concurrent.atomic.AtomicInteger;

public class Voliatetest {
    //private static  volatile int  num=0;  volatile 不保证原子性
   private static AtomicInteger num=new AtomicInteger();
     public static void   add(){
         //num++;
        num.getAndIncrement();
    }
    public static void main(String[] args) {
         for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    add();
                }
            }).start();
        }
        while (Thread.activeCount()>2){//保证线程执行完，如果大于2，说明有线程未执行完，main线程让步
             Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+num);
    }
}
