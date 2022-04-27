package com.forkjoin;

import java.util.stream.LongStream;

public class ForLoop {

    public long sum(long[] nums) {
      long total=0;
        for (Long num : nums){
            total=total+num;
        }

        return total;
    }


    public static void main(String[] args) {
        long[] nums= LongStream.rangeClosed(1,100000000).toArray();
       long start=System.currentTimeMillis();
        ForLoop forLoop = new ForLoop();
        long sum = forLoop.sum(nums);
        long end=System.currentTimeMillis();
        System.out.println(sum+"耗时"+(end-start)+"ms");

    }
}
