package com.colak.concurrent.completablefuture.delayedexecutor;

import java.util.concurrent.*;
import java.util.concurrent.ThreadLocalRandom;

// Normally, supplyAsync runs immediately on a thread pool.
// The delayedExecutor delays task execution by 1 second, simulating a non-blocking delay instead of using Thread.sleep().
public class DelayedExecutorTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis(); // Start time for performance measurement

        // Task 1: Generates a random integer between 1 and 100 after a 1-second delay
        CompletableFuture<Integer> task1 = CompletableFuture.supplyAsync(() -> {
            int num = ThreadLocalRandom.current().nextInt(1, 100);
            System.out.println("Task 1 generated: " + num);
            return num;
        }, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));

        // Task 2: Generates another random integer between 1 and 100 after a 1-second delay
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> {
            int num = ThreadLocalRandom.current().nextInt(1, 100);
            System.out.println("Task 2 generated: " + num);
            return num;
        }, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));

        // Combine both results (sum of task1 and task2 results)
        CompletableFuture<Integer> result = task1.thenCombine(task2, (num1, num2) -> {
            System.out.println("Combining results: " + num1 + " + " + num2);
            return num1 + num2;
        });

        // Get and print the final result
        System.out.println("Final Result: " + result.get());

        long end = System.currentTimeMillis(); // End time for performance measurement
        System.out.println("Execution Time: " + (end - start) + "ms");
    }
}

