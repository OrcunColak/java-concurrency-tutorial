package com.colak.concurrent.executorservice.shutdownnow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// ExecutorService provides methods to manage thread pools:
//
// 1. shutdown():
// Prevents new tasks from being submitted.
// Allows currently running tasks to complete.

// 2. shutdownNow():
// Attempts to stop all running tasks immediately.
// Interrupts threads performing blocking operations.

public class ExecutorServiceShutdownNowTest {

    public static void main() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Task 1 running...");
                try {
                    Thread.sleep(1000); // Simulate work
                } catch (InterruptedException e) {
                    System.out.println("Task 1 interrupted.");
                }
            }
        });

        executor.submit(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Task 2 running...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Task 2 interrupted.");
                }
            }
        });

        Thread.sleep(3000); // Let tasks run for 3 seconds
        executor.shutdownNow(); // Attempt to stop all tasks immediately
    }
}
