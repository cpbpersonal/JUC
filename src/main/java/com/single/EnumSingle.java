package com.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

//防止反射的破坏
public enum  EnumSingle {
    INSTANCE;
    EnumSingle(){}
    public static EnumSingle getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) throws Exception {
       // Constructor<EnumSingle> declaredConstructor = EnumSingle.class.getDeclaredConstructor();
        Constructor<EnumSingle> declaredConstructor1 = EnumSingle.class.getDeclaredConstructor(String.class,int.class);

        declaredConstructor1.setAccessible(true);
        EnumSingle enumSingle1 = declaredConstructor1.newInstance();
        EnumSingle enumSingle2 = declaredConstructor1.newInstance();
        System.out.println(enumSingle1);
        System.out.println(enumSingle2);

    }

}
