package com.single;
public class Lazy2 {
    private Lazy2(){
        System.out.println(Thread.currentThread().getName());
    }
    private volatile static Lazy2 lazy2;
    public static Lazy2 getinstance(){
        if(lazy2==null){
            synchronized (Lazy2.class){
                if (lazy2==null){lazy2=new Lazy2();} }
        }return lazy2;
    }
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{ getinstance();}).start();
        }
    }
}
