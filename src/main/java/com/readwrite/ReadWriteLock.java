package com.readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {

    public static void main(String[] args) {
        mycache mycache = new mycache();
        for ( int i = 0; i < 5; i++) {
            final int tem=i;
            new Thread(()->{
                mycache.put(tem+"",tem+"");
            },String.valueOf(i)).start();
        }
        for ( int i = 0; i < 5; i++) {
            final int tem=i;
            new Thread(()->{
                mycache.get(tem+"");
            },String.valueOf(i)).start();
        }

    }
    static class mycache{
     private volatile   Map<String,Object> map= new HashMap<>();
     private ReentrantReadWriteLock readWriteLock=new ReentrantReadWriteLock();
      private void put(String s,Object o){
          //写锁，排它锁，独占锁
            readWriteLock.writeLock().lock();
          System.out.println(Thread.currentThread().getName()+"开始写入");
          map.put(s, o);
          System.out.println(Thread.currentThread().getName()+"写入完成");
          readWriteLock.writeLock().unlock();
      }
        private void get(String s){
          //读锁，共享锁
            readWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName()+"读取开始");
            map.get(s);
            System.out.println(Thread.currentThread().getName()+"读取完成");
            readWriteLock.readLock().unlock();
        }

    }
}
