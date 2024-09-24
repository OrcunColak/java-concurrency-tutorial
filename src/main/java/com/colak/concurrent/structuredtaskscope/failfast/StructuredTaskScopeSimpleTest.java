package com.colak.concurrent.structuredtaskscope.failfast;

import java.util.concurrent.StructuredTaskScope;

// This test does not return an error. Boths tasks complete successfully
public class StructuredTaskScopeSimpleTest {

    // Old way with Executors
    // public static void main(String[] args) throws InterruptedException, ExecutionException {
    //     ExecutorService executor = Executors.newFixedThreadPool(2);
    //
    //     // Submit tasks to the executor
    //     Future<Integer> future1 = executor.submit(() -> {
    //         System.out.println("Task 1 running...");
    //         return 10;
    //     });
    //     Future<Integer> future2 = executor.submit(() -> {
    //         System.out.println("Task 2 running...");
    //         return 20;
    //     });
    //
    //     // Wait for both tasks to complete
    //     int result = future1.get() + future2.get();
    //     System.out.println("Result: " + result);
    //
    //     // Shutdown the executor manually
    //     executor.shutdown();
    // }

    // Old way with ForkJoin
    // public static void main(String[] args) throws InterruptedException, ExecutionException {
    //     ForkJoinPool forkJoinPool = new ForkJoinPool();
    //     // Submit tasks to ForkJoinPool
    //     ForkJoinTask<Integer> task1 = forkJoinPool.submit(() -> {
    //         System.out.println("Task 1 running...");
    //         return 10;
    //     });
    //
    //     ForkJoinTask<Integer> task2 = forkJoinPool.submit(() -> {
    //         System.out.println("Task 2 running...");
    //         return 20;
    //     });
    //
    //     // Wait for tasks to complete
    //     int result = task1.get() + task2.get();
    //     System.out.println("Result: " + result);
    //     // Shutdown the ForkJoinPool
    //     forkJoinPool.shutdown();
    // }

    public static void main() throws Exception {

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            // Submit tasks within the structured scope
            var future1 = scope.fork(() -> {
                System.out.println("Task 1 running...");
                return 10;
            });
            var future2 = scope.fork(() -> {
                System.out.println("Task 2 running...");
                return 20;
            });

            // Wait for all tasks to complete
            scope.join(); // Blocks until all tasks are done
            scope.throwIfFailed(); // Handles exceptions centrally
            // Combine the results
            int result = future1.get() + future2.get();
            System.out.println("Result: " + result);
        }
        // No need to manually shutdown, resources are cleaned up automatically
    }
}
