package com.colak.concurrent.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


 // See https://hemanthcse1.medium.com/interrupts-in-java-5bfcd752fadc

@Slf4j
class ThreadInterruptTest {

    public static void main() throws InterruptedException {
        Thread newThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                // Long-running task
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException exception) {
                    // Handle the interruption or re-interrupt the thread
                    Thread.currentThread().interrupt();
                    log.info("InterruptedException caught", exception);
                }
            }
        });

        newThread.start();
        log.info("Thread started");

        TimeUnit.SECONDS.sleep(1);

        // Later in the code
        newThread.interrupt(); // Set the interrupt flag
        log.info("Thread interrupted");

        newThread.join();
        log.info("Test finished");
    }
}
