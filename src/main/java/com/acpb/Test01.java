package com.acpb;

import java.util.concurrent.locks.Lock;

public class Test01 {

    public static void main(String[] args) {

        ticket ticket = new ticket();


            new Thread( ()->{
                for (int i = 0; i <50 ; i++) {
                    ticket.sale();
                }

            },"A").start();

            new Thread( ()->{
                for (int i = 0; i <50 ; i++) {
                    ticket.sale();
                }
            },"B").start();

            new Thread( ()->{
                for (int i = 0; i <50 ; i++) {
                    ticket.sale();
                }
            },"C").start();





    }





  static class  ticket{
        private int numbers=50;

        public synchronized void sale(){
            if (numbers>0){
                numbers--;
                System.out.println(Thread.currentThread().getName()+"卖出了第"+(50-numbers)+"张票，剩余"+numbers+"张票");
            }
        }

    }
}
