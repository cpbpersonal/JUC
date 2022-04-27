package com.cas;

import java.util.concurrent.atomic.AtomicReference;

public class MyLock {

    AtomicReference<Thread> atomicReference=new AtomicReference<>();
    public  void lock(){
        Thread thread=Thread.currentThread();
        while (!atomicReference.compareAndSet(null,thread)){
            System.out.println(Thread.currentThread().getName());
        }
    }
    public  void unlock(){
        Thread thread=Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"解锁");
        atomicReference.compareAndSet(thread,null);

    }

}
