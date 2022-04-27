package com.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Lazy4 {
    private Lazy4(){
        synchronized (Lazy4.class){ if (lazy4!=null){
            throw  new RuntimeException("试图反射");
        }
        }

    }
    private  static volatile Lazy4 lazy4;

    public static Lazy4 getinstance(){
        if (lazy4 ==null){
          synchronized (Lazy4.class){
              if (lazy4==null) lazy4=new Lazy4();
          }
        }
       return lazy4;
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Lazy4 lazy4=Lazy4.getinstance();
        Class lazyclass=Lazy4.class;
        Constructor <Lazy4>declaredConstructor = lazyclass.getDeclaredConstructor(null);
           Lazy4  lazy41 =declaredConstructor.newInstance();
           Lazy4  lazy42  =declaredConstructor.newInstance();
            System.out.println(lazy41);
            System.out.println(lazy42);
           // System.out.println(lazy41==lazy42);
    }
}
