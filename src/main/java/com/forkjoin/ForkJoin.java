package com.forkjoin;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoin {
    //private ForkJoinPool pool;

   //执行任务RecursiveTask：有返回值  RecursiveAction：无返回值
   private static class sumtask extends RecursiveTask<Long> {
       private long[] numbers;
       private int from;
       private int to;

       public sumtask(long[] numbers, int from, int to) {
           this.numbers = numbers;
           this.from = from;
           this.to = to;
       }
        @Override
        protected Long compute() {
            if(to-from<6){
                long total = 0;
                for (int i = from; i <= to; i++) {
                    total += numbers[i];
                }
                return total;
            }else {
                int mid=(from+to)/2;
                sumtask sumtask1=new sumtask(numbers,from,mid);
                sumtask sumtask2=new sumtask(numbers,mid+1, to);
                sumtask1.fork();
                sumtask2.fork();
                return sumtask1.join()+sumtask2.join();

            }

        }


    }
    public long sum(long[] nums) {

        Long result = pool.invoke(new sumtask(nums, 0, nums.length - 1));
        pool.shutdown();
        return result;

    }

      ForkJoinPool  pool = new ForkJoinPool();




    public static void main(String[] args) {
        long[] numbers = LongStream.rangeClosed(1, 100000000).toArray();

        Instant start = Instant.now();
        ForkJoin forkJoin = new ForkJoin();
        long result = forkJoin.sum(numbers);
        Instant end = Instant.now();
        System.out.println(result+"耗时：" + Duration.between(start, end).toMillis() + "ms");

    }

}
