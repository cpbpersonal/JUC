package com.cas;

import sun.misc.Unsafe;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
//ABA问题解决
public class CasTest {
    public static void main(String[] args) {
        AtomicStampedReference<Integer>atomicReference=new AtomicStampedReference<>(1,0);
        new Thread(()->{
            System.out.println("a1 version=>"+atomicReference.getStamp());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicReference.compareAndSet(1,2,
                    atomicReference.getStamp(), atomicReference.getStamp()+1);
            System.out.println("a2 version=>"+atomicReference.getStamp());
            System.out.println(atomicReference.compareAndSet(2, 1,
                    atomicReference.getStamp(), atomicReference.getStamp() + 1));
            System.out.println("a3 version=>"+atomicReference.getStamp());
        },"A").start();
        new Thread(()->{
            int stamp=atomicReference.getStamp();//拿到版本号0
            System.out.println("b1 version=>"+stamp);
            try {//sleep中线程A将1改为2，又改回1，版本号为+2
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(1, 6,
                    stamp, stamp + 1));//传入版本号为0，实际已经是2了，对比版本号不同修改失败
            System.out.println("b2 version=>"+stamp + 1);
        },"B").start();
    }
}
