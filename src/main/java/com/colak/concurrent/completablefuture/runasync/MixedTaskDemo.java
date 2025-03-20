package com.colak.concurrent.completablefuture.runasync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

// See https://medium.com/tuanhdotnet/understanding-cpu-and-i-o-bound-operations-a-guide-for-developers-a9eca3f9d227

//  What Are CPU-bound Operations?
// CPU-bound tasks are those where the speed of execution is limited by the processor’s speed.

// What Are I/O-bound Operations?
// I/O-bound tasks are limited by the speed of input/output operations rather than by the CPU.

// Some tasks might involve a mix of both CPU and I/O-bound operations.
// For instance, a web server handling requests from clients is often both CPU-bound (when processing data) and
// I/O-bound (when reading from or writing to databases).
// Managing such tasks efficiently requires understanding both CPU and I/O limitations.

// Consider a web server that processes user requests, performs calculations, and fetches data from a database.
// This example showcases both CPU-bound (computation) and I/O-bound (simulated database access with Thread.sleep) operations.
// Balancing these two types of operations is key to optimizing such mixed workloads.
public class MixedTaskDemo {

    public static void main() throws ExecutionException, InterruptedException {

        CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
            // Simulate CPU-bound operation
            for (int i = 0; i < 1_000_000; i++) {
                // Some heavy computation
                Math.sqrt(i);
            }
            System.out.println("CPU-bound task finished");
        }).thenRunAsync(() -> {
            // Simulate I/O-bound operation (e.g., database access)
            try {
                Thread.sleep(2000); // Simulate I/O delay
                System.out.println("I/O-bound task finished");
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        });

        task.get();
    }
}
