package com.acpb;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class locktest {


        public static void main(String[] args) {


            com.acpb.Test01.ticket ticket = new com.acpb.Test01.ticket();


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
            Lock lock =new ReentrantLock();
            private int numbers=50;

            public  void sale(){
                lock.lock();//加锁
                try {if (numbers>0){
                    numbers--;
                    System.out.println(Thread.currentThread().getName()+"卖出了第"+(50-numbers)+"张票，剩余"+numbers+"张票");
                }

                }catch (Exception e){
                 e.printStackTrace();
                }//解锁
                finally{ lock.unlock();}

            }

        }
    }


