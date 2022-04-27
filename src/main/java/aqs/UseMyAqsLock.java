package aqs;
import java.util.concurrent.TimeUnit;

public class UseMyAqsLock {

    public static int num;

    public static void main(String[] args) throws InterruptedException {
        MyAqsLock myLock= new MyAqsLock();
       Thread []threads=new Thread[100];
        for (int i = 0; i <threads.length ; i++) {
            threads[i]=new Thread(()->{
                //myLock.lock();
                for (int j = 0; j < 100; j++) {
                    num++;
                }
                //myLock.unlock();
            });
        }
        for (Thread t:threads) { t.start(); }
        for (Thread t:threads) {t.join(); }
        System.out.println(num);
    }
}
