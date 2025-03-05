package com.colak.concurrent.thread.virtual;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

// See https://blog.stackademic.com/unveiling-the-power-of-virtual-threads-in-java-a-deep-dive-0c96fe50a3cb
@Slf4j
class VirtualThreadCountdownLatchTest {

    public static void main() throws InterruptedException {

        int numberOfThreads = 2;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);


        Runnable task = () -> {
            log.info("is doing some work.");
            // Simulate some work
            sleep();

            log.info("has completed its work.");
            latch.countDown();
        };

        Thread virtualThread1 = Thread.ofVirtual().name("virtual-1").start(task);
        Thread virtualThread2 = Thread.ofVirtual().name("virtual-2").start(task);

        virtualThread1.join();
        virtualThread2.join();
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
