package com.single;

public class Lazy1 {
    private Lazy1 (){//构造器私有化，创建对象时调用构造器输出当前线程
        System.out.println(Thread.currentThread().getName());
    }
    private static Lazy1 lazy1;
    public static Lazy1 getinstance(){
        if (lazy1==null){
            lazy1=new Lazy1();
        }
        return lazy1;
    }
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{ getinstance();}).start();
        }
    }
}
