package com.cas;

import java.util.concurrent.TimeUnit;

public class UseMyLock {
    public static void main(String[] args) {
        MyLock myLock= new MyLock();
        new Thread(()->{
            myLock.lock();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                myLock.unlock();
            }
        },"A").start();
        new Thread(()->{
            myLock.lock();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                myLock.unlock();
            }
        },"B").start();
    }
}
