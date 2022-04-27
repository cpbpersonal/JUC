package com.acpb;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test1 {

    public static void main(String[] args) {
        data data = new data();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                data.incre();
            }
            },"A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
            data.descr();
        };},"B").start();
    }

    static class data{
        Lock lock=new ReentrantLock();
        int nums=0;
        Condition condition1=lock.newCondition();
        Condition condition2=lock.newCondition();
        public void  incre(){
            lock.lock();
            try {
                while (nums!=0){
                condition1.await();
                }
                System.out.println(Thread.currentThread().getName());
                nums++;
                condition2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }

        public void  descr(){

            lock.lock();
            try {
                while (nums==0){
                    condition2.await();
                }
                System.out.println(Thread.currentThread().getName());
                nums--;
                condition1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }


        }

    }
}
