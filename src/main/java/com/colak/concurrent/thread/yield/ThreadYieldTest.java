package com.colak.concurrent.thread.yield;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


// See https://medium.com/@avinashsoni9829/java-threads-internals-yield-method-part-2-889b092c31bc
//
// Thread.yield() is hint to the scheduler that the current thread is willing to yield its current use of a processor.
// The scheduler is free to ignore this hint

@Slf4j
@UtilityClass
class ThreadYieldTest {

    public static class YieldRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; ++i) {
                log.info(" : Yield Method Check : {}", i);
                Thread.yield();
            }
        }
    }

    public static void main() {
        YieldRunnable yieldRunnable = new YieldRunnable();
        Thread th1 = new Thread(yieldRunnable);
        Thread th2 = new Thread(yieldRunnable);
        th1.setName("thread1 ");
        th2.setName("thread2");
        th1.start();
        th2.start();
    }
}

