package com.single;

public class StaticInnerClass {
    private StaticInnerClass(){}
    //静态内部类
    public static  class InnerClass{
        private static final StaticInnerClass staticInnerClass=new StaticInnerClass();
    }
    public static StaticInnerClass getinstance(){
        return InnerClass.staticInnerClass;
    }
}
