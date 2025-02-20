package com.colak.concurrent.structuredtaskscope.failfast;

import java.util.concurrent.Semaphore;
import java.util.concurrent.StructuredTaskScope;

public class StructuredTaskScopeSemaphoreTest {

    public static void main() {
        Semaphore semaphore = new Semaphore(2); // Allow only 2 tasks to run at a time

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            StructuredTaskScope.Subtask<String> task1 = scope.fork(() -> runTask("Task 1", 2, semaphore));
            StructuredTaskScope.Subtask<String> task2 = scope.fork(() -> runTask("Task 2", 4, semaphore));
            StructuredTaskScope.Subtask<String> task3 = scope.fork(() -> runTask("Task 3", 3, semaphore));

            scope.join();  // Wait for tasks
            scope.throwIfFailed(); // If any task fails, shutdown remaining tasks

            System.out.println("Results: " + task1.get() + ", " + task2.get() + ", " + task3.get());
        } catch (Exception e) {
            System.err.println("One of the tasks failed: " + e.getMessage());
        }
    }

    private static String runTask(String name, int duration, Semaphore semaphore) throws InterruptedException {
        semaphore.acquire(); // Acquire a permit before execution
        try {
            System.out.println(name + " started...");
            Thread.sleep(duration * 1000);
            if ("Task 2".equals(name)) throw new RuntimeException(name + " failed!"); // Simulate failure
            System.out.println(name + " completed.");
            return name + " result";
        } finally {
            semaphore.release(); // Release the permit
        }
    }
}


