package com.hewking.develop.concurrent;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @Description: 第一个一个线程输出n...n+1..n+2 ...n + 10,第二个线程输出 10 + n .. 10 + n+2..
 * 保证顺序
 * @Author: jianhao
 * @Date: 2021/2/19 18:33
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box,  and it is prohibited to leak or used for other commercial purposes.
 */
public class ConcurrentTaskTest {

    /**
     * 100个数字顺序打印，10个线程每个线程打印10个数字
     */
    @Test
    public void test(){
        int count = 10;
        CountDownLatch firstLatch = new CountDownLatch(1);
        CountDownLatch latch = firstLatch;
        CountDownLatch endLatch = new CountDownLatch(1);
        for (int i = 0 ; i < count; i ++) {
            new Worker(latch,i,endLatch).start();
            latch = endLatch;
            endLatch = new CountDownLatch(1);
        }

        firstLatch.countDown();
        endLatch.countDown();
        try {
            endLatch.await();
            System.out.println("task complete!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static class Worker extends Thread{

        private CountDownLatch downLatch;
        private CountDownLatch endCountDownLatch;
        private int num;

        Worker(CountDownLatch downLatch, int num, CountDownLatch endCountDownLatch) {
            this.downLatch = downLatch;
            this.num = num;
            this.endCountDownLatch = endCountDownLatch;
        }

        @Override
        public void run() {
            super.run();
            try {
                downLatch.await();

                for (int i = 1 ; i <= 10 ; i ++) {
                    System.out.println(num * 10 + i);
                }

                endCountDownLatch.countDown();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
