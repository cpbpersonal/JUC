package com.voliate;
import java.util.concurrent.TimeUnit;

public class voliatetest1 {
 private static  volatile    int num=0;//加volatile关键字后，thread中的值会从主存更新
    public static void main(String[] args) throws InterruptedException {

        new Thread(()->{//副本中的值为0，
            while (num==0){
                System.out.println(num);
            }

        }).start();
        TimeUnit.SECONDS.sleep(1);
        num=1;//在main线程中修改值为1，但另一个线程不可见，仍然在循环
        System.out.println(num);
    }
}
