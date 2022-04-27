package ConcurrentLeetCode;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

class ZeroEvenOdd {
    private int n;
    Semaphore zero =new Semaphore(1);
    Semaphore even=new Semaphore(0);
    Semaphore odd =new Semaphore(0);

    public ZeroEvenOdd(int n) {
        this.n = n;
    }
    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            zero.acquire();
            printNumber.accept(0);
            if (i%2==0){even.release();}//先输出奇数
            else     {odd.release();}//再输出偶数
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <=n; i+=2) {
            even.acquire();
           printNumber.accept(i);
            zero.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i+=2) {
            odd.acquire();
            printNumber.accept(i);
            zero.release();
        }
    }

    public static void main(String[] args) {
        ZeroEvenOdd zeroEvenOdd=new ZeroEvenOdd(5);
          IntConsumer intConsumer=new IntConsumer() {
              @Override
              public void accept(int value) {
                  System.out.println("线程"+Thread.currentThread().getName()+":"+value);
              }
          };
        new Thread(()->{
            try {
                zeroEvenOdd.zero(intConsumer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"0").start();
        new Thread(()->{
            try {
                zeroEvenOdd.even(intConsumer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"1").start();
        new Thread(()->{
            try {
                zeroEvenOdd.odd(intConsumer);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"2").start();
    }
}

