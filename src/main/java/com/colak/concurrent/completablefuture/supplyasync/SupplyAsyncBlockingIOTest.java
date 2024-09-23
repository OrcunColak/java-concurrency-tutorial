package com.colak.concurrent.completablefuture.supplyasync;

import java.util.concurrent.CompletableFuture;

class SupplyAsyncBlockingIOTest {

    public static void main() {
        CompletableFuture.supplyAsync(SupplyAsyncBlockingIOTest::performIO)
                .thenAccept(result -> System.out.println("I/O Result: " + result))
                .exceptionally(ex -> {
                    System.err.println("Error: " + ex.getMessage());
                    return null;
                });
    }

    private static String performIO() {
        // Simulate I/O operation
        return "I/O Operation Completed";
    }
}
