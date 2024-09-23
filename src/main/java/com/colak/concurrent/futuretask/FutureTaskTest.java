package com.colak.concurrent.futuretask;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
class FutureTaskTest {

    public static void main() {
        // Create a Callable task
        Callable<Integer> task = () -> {
            // Simulate some computation
            Thread.sleep(2000);
            return 123;
        };

        // Create a FutureTask with the Callable
        FutureTask<Integer> futureTask = new FutureTask<>(task);

        // Create a thread to execute the FutureTask
        Thread thread = new Thread(futureTask);
        thread.start();

        // Do some other work while the task is running...

        try {
            // Wait for the task to complete and get the result
            int result = futureTask.get();
            log.info("Result: {}", result);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Exception", e);
            Thread.currentThread().interrupt();
        }
    }
}
