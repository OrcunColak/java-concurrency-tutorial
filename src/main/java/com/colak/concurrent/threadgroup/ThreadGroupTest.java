package com.colak.concurrent.threadgroup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class ThreadGroupTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group = new ThreadGroup("MyThreadGroup");
        Thread thread1 = new Thread(group, () -> {
            log.info("Thread 1");
        });
        Thread thread2 = new Thread(group, () -> {
            log.info("Thread 2");
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }
}
