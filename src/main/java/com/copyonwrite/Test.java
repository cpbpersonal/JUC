package com.copyonwrite;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Test {


    // ArriList线程不安全，多线程插入操作时会报错 java.util.ConcurrentModificationException
    // 1.List<String>list=new Vector<>(); //使用线程安全的vector
    //2.List<String>list= Collections.synchronizedList(new ArrayList<>());  //使用collections工具类
    //3.List<String>list= new CopyOnWriteArrayList<>();
    public static void main(String[] args) {
        List<String>list= new CopyOnWriteArrayList<>();
        for (int i = 0; i < 50; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(list);
            }).start();
        }
    }
}
