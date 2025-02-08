package com.colak.concurrent.thread.interrupt;

import lombok.extern.slf4j.Slf4j;


@Slf4j
class ThreadInterruptTest2 {

    public void main() throws InterruptedException {
        Thread thread = new Thread(new InterruptExample());
        thread.start();

        Thread.sleep(3000); // Let the thread run for 3 seconds
        thread.interrupt(); // Interrupt the thread
    }

    static class InterruptExample implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Running...");
                try {
                    Thread.sleep(1000); // Simulate work
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted during sleep.");
                    break; // Exit the loop if interrupted
                }
            }
            System.out.println("Thread gracefully stopped.");
        }
    }
}
