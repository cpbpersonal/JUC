package com.single;

public class Lazy5 {
  private Lazy5(){};
  private volatile static Lazy5 lazy5;

  public static Lazy5 getinstance (){
      if( lazy5==null){
          synchronized (Lazy5.class){
              if (lazy5==null){
                  lazy5=new Lazy5();
              }
          }

      }
      return lazy5;
  }
}
