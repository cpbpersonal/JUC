package com.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Lazy3 {
    private Lazy3(){}
    private volatile static Lazy3 lazy3;
    public static Lazy3 getinstance(){
        if(lazy3==null){
            synchronized (Lazy2.class){
                if (lazy3==null){lazy3=new Lazy3();} }
        }return lazy3;
    }
    public static void main(String[] args) throws Exception {
        //反射拿到类的构造器设置为可改变，通过类的构造器直接创建出多个对象
        Constructor<Lazy3> declaredConstructor = Lazy3.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        Lazy3 lazya = declaredConstructor.newInstance();
        Lazy3 lazyb = declaredConstructor.newInstance();
        System.out.println(lazya);
        System.out.println(lazyb);
    }
}
