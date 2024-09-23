package com.colak.concurrent.completablefuture.thenaccept;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@UtilityClass
public class CompletableFutureThenAcceptTest {

    public static void main() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // Simulate a long-running task
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of the asynchronous computation";
        });

        future.thenAccept(result -> log.info("thenAccept Result : {}", result));

        log.info("Result : {}", future.get());
    }
}
