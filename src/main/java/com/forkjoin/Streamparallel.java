package com.forkjoin;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

public class Streamparallel {

    public static void main(String[] args) {

        Instant start = Instant.now();
        long result = LongStream.rangeClosed(0, 100000000).parallel().reduce(0, Long::sum);
        Instant end = Instant.now();
        System.out.println("耗时：" + Duration.between(start, end).toMillis() + "ms");

        System.out.println("结果为：" + result); // 打印结果500500

    }
}
