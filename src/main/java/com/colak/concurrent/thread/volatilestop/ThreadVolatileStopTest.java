package com.colak.concurrent.thread.volatilestop;

public class ThreadVolatileStopTest {

    public void main() throws InterruptedException {
        StoppableThread task = new StoppableThread();
        Thread thread = new Thread(task);
        thread.start();

        Thread.sleep(2000); // Let the thread run for 2 seconds
        task.stop(); // Signal the thread to stop
    }

    static class StoppableThread implements Runnable {
        private volatile boolean running = true; // Flag to control the thread

        @Override
        public void run() {
            while (running) {
                System.out.println("Thread is running...");
                try {
                    Thread.sleep(500); // Simulate some work
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                    break; // Stop if interrupted
                }
            }
            System.out.println("Thread stopped.");
        }

        // Method to stop the thread
        public void stop() {
            running = false;
        }
    }
}
