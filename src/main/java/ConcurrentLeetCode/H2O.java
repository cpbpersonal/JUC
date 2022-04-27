package ConcurrentLeetCode;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

class H2O {

    public H2O() {

    }
    Semaphore h=new Semaphore(2);
    Semaphore o=new Semaphore(0);
    CyclicBarrier cb=new CyclicBarrier(3,()->{
       h.release(2);
       o.release(1);
    });

    public void hydrogen(Runnable releaseHydrogen) throws Exception {

        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
       h.acquire();
        releaseHydrogen.run();
        o.release();
        //cb.await();

    }

    public void oxygen(Runnable releaseOxygen) throws Exception {

        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        o.acquire(2);
        releaseOxygen.run();
        h.release(2);
        //cb.await();


    }

    public static void main(String[] args) throws InterruptedException {
        H2O h2O=new H2O();
        int n=6;
      new Thread(()->{
          for (int i = 0; i < n; i++) {
              try {
                  h2O.hydrogen(()->{
                      System.out.println("H");
                  });
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }

      },"H").start();
        new Thread(()->{
            for (int i = 0; i < n; i++) {
                try {
                    h2O.oxygen(()->{
                        System.out.println("O");
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        },"O").start();



    }
}