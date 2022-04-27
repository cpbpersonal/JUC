package com.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class cycli {
    public static void main(String[] args)  {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("召唤神龙");
        });
        for (int i = 0; i < 7; i++) {
            new Thread(()->{
                System.out.println("拿到了1个龙珠");
                try {
                    cyclicBarrier.await();//每个线程到这里都会等待，计数器数量为7才放行
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }


    }
}
