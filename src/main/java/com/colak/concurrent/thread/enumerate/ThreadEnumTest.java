package com.colak.concurrent.thread.enumerate;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


// See https://medium.com/@avinashsoni9829/java-threads-internals-enumerate-method-part-8-727b9e07bd66
// The Thread.enumerate() method only provides information about the threads in the same thread group.
// Threads in different thread groups will not be included in the array returned by this method

@Slf4j
class ThreadEnumTest {

    public static void main() {
        Thread myThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                java.lang.Thread.currentThread().interrupt();
            }
        });
        myThread.setName("MyThread1");
        myThread.start();


        Thread myThread2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                java.lang.Thread.currentThread().interrupt();
            }
        });
        myThread2.setName("MyThread2");
        myThread2.start();

        Thread[] allThreads = new Thread[java.lang.Thread.activeCount()];
        java.lang.Thread.enumerate(allThreads);

        log.info("Active Threads ");

        for (Thread t : allThreads) {
            if (t != null) log.info(t.getName());
        }

    }
}
