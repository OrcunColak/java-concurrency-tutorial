package com.colak.concurrent.thread.virtual;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

// See https://blog.stackademic.com/unveiling-the-power-of-virtual-threads-in-java-a-deep-dive-0c96fe50a3cb
@Slf4j
class VirtualThreadCompletableFutureTest {

    public static void main() throws InterruptedException {

        ThreadFactory factory = Thread.ofVirtual().name("virtual", 1).factory();
        try (ExecutorService myExecutor = Executors.newThreadPerTaskExecutor(factory)) {

            CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
                log.info("is computing a result.");
                // Simulate some work
                sleep();
                return 42;
            }, myExecutor);

            CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
                log.info(" is computing another result.");
                // Simulate some work
                sleep();
                return 10;
            }, myExecutor);

            CompletableFuture<Integer> combinedFuture = future1.thenCombineAsync(future2, Integer::sum, myExecutor);

            combinedFuture.thenAcceptAsync(result -> log.info("Combined result: " + result), myExecutor);

            // Wait for the combined future to complete
            combinedFuture.join();
        }
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
