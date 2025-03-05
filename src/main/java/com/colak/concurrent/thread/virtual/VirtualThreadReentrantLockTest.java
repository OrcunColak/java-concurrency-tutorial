package com.colak.concurrent.thread.virtual;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

// See https://blog.stackademic.com/unveiling-the-power-of-virtual-threads-in-java-a-deep-dive-0c96fe50a3cb

// See https://medium.com/@phil_3582/java-virtual-threads-some-early-gotchas-to-look-out-for-f65df1bad0db
// Any libraries used by virtual threads might use synchronized methods or synchronized blocks around long IO operations.
// If so, this will pin the virtual threads to their carrier threads during the IO operations thereby limiting any performance advantage. You might be able to work around this but ultimately synchronized methods and blocks should be replaced with ReentrantLock. This has already been done in the Java libraries but there are still thousands of third party libraries which may have this problem.
@Slf4j
class VirtualThreadReentrantLockTest {

    public static void main() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        Runnable task = () -> {
            lock.lock();
            try {
                log.info("acquired the lock.");

                // Simulate some work
                sleep();

                log.info("released the lock.");
            } finally {
                lock.unlock();
            }
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
