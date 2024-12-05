package com.colak.concurrent.threadlocal;

import lombok.extern.slf4j.Slf4j;

// This test shows that main, thread1, and thread2 each see its own version of ThreadLocal
@Slf4j
class ThreadLocalTest {

    private static final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    public static void main() {
        mainThreadTest();
        twoThreadsTest();
    }

    private static void mainThreadTest() {
        threadLocal.set(42); // Set a thread-local variable
        int value = threadLocal.get();
        log.info("Main Thread : {}", value);
    }

    private static void twoThreadsTest() {
        Thread thread1 = new Thread(() -> {
            threadLocal.set(1);
            log.info("Thread 1: {}", threadLocal.get());
        });

        Thread thread2 = new Thread(() -> {
            threadLocal.set(2);
            log.info("Thread 2: {}", threadLocal.get());
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException exception) {

        }
    }
}
