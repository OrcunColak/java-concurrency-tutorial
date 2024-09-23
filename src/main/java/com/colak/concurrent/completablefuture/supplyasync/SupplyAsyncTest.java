package com.colak.concurrent.completablefuture.supplyasync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SupplyAsyncTest {

    public static void main() {
        try (ExecutorService executorService =
                     Executors.newFixedThreadPool(2,
                             (Runnable r) -> {
                                 Thread t = new Thread(r);
                                 t.setDaemon(true);
                                 return t;
                             }
                     )) {

            CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(() -> {
                // Simulate a long-running task
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                log.info("Task 1 completed by: {}", Thread.currentThread().getName());
                return null;
            }, executorService);

            CompletableFuture<Void> future2 = CompletableFuture.supplyAsync(() -> {
                // Another long-running task
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                log.info("Task 2 completed by: {}", Thread.currentThread().getName());
                return null;
            }, executorService);

            // Wait for both futures to complete
            CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2);
            combinedFuture.join(); // Blocks until all futures are complete

            log.info("Both tasks completed.");
        }
    }
}
