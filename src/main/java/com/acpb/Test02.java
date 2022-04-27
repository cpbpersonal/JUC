package com.acpb;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test02 {
    public static void main(String[] args) {
        data data = new data();
        new Thread(() ->{for (int i = 0; i < 10; i++)data.printA();},"A").start();
        new Thread(() ->{for (int i = 0; i < 10; i++) data.printB();},"B").start();
        new Thread(() ->{for (int i = 0; i < 10; i++)data.printC(); },"C").start();
        new Thread(() ->{for (int i = 0; i < 10; i++)data.printD(); },"D").start();
    }


    static class data{
        Lock lock =new ReentrantLock();
        Condition condition1=lock.newCondition();
        Condition condition2=lock.newCondition();
        Condition condition3=lock.newCondition();
        Condition condition4=lock.newCondition();
        int i = 0;
        private void printA() {

            lock.lock();
            try {//生产者，不为0就等待，为0就生产
                while (i != 0) {
                    condition1.await();

                }
                System.out.println(Thread.currentThread().getName());
                i++;
                condition2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
            private void printB() {
                lock.lock();
                try {//消费者，等于0就等待，不等于0就消费
                    while (i == 0) {
                        condition2.await();

                    }
                    System.out.println(Thread.currentThread().getName());
                    i--;
                    condition3.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        private void printC() {
            lock.lock();
            try {//生产者，不等于0就等待，等于0就生产
                while (i != 0) {
                    condition3.await();

                }
                System.out.println(Thread.currentThread().getName());
                i++;
                condition4.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        private void printD() {

            lock.lock();
            try {//消费者
                while (i == 0) {
                    condition4.await();

                }
                System.out.println(Thread.currentThread().getName());
                i--;
                condition1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }








    }
}
