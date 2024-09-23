package com.colak.concurrent.thread.notify;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


// https://medium.com/@reetesh043/understanding-the-difference-between-wait-and-sleep-methods-64958feffbb8

@Slf4j
class ThreadNotifyTest {

    private static class MyWaitingThread extends Thread {
        @Override
        public void run() {
            synchronized (this) {
                log.info("Thread 1 is waiting...");
                try {
                    this.wait(); // Thread 1 waits until notified by another thread
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                log.info("Thread 1 has been notified.");
            }
        }
    }

    public static void main() throws InterruptedException {
        MyWaitingThread thread1 = new MyWaitingThread();
        thread1.start();

        // Sleep for 1 second
        TimeUnit.SECONDS.sleep(1);

        synchronized (thread1) {
            // Notify thread1 to resume
            thread1.notify();
        }
        thread1.join();
    }
}
