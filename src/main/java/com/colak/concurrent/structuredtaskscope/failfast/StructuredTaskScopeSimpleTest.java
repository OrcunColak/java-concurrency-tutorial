package com.colak.concurrent.structuredtaskscope.failfast;

import java.util.concurrent.StructuredTaskScope;

// This test does not return an error. Boths tasks complete successfully
public class StructuredTaskScopeSimpleTest {

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
