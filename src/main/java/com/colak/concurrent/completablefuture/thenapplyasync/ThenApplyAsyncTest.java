package com.colak.concurrent.completablefuture.thenapplyasync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class ThenApplyAsyncTest {

    public static void main() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenApplyAsync(greeting -> greeting + " World")
                .thenApplyAsync(message -> message + "!");
        System.out.println(future.get()); // Prints "Hello World!"
    }
}
