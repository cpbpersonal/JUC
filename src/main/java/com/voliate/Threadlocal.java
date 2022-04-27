package com.voliate;

import java.util.concurrent.TimeUnit;

public class Threadlocal {
    public static void main(String[] args) {
        ThreadLocal<Person> tl = new ThreadLocal<>();
        ThreadLocal<Person> tl2 = new ThreadLocal<>();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tl.get());
        }).start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person());
            tl2.set(new Person());
            System.out.println(tl2.get());
            System.out.println(tl.get());
            tl.remove();
            tl2.remove();
        }).start();
    }
}
