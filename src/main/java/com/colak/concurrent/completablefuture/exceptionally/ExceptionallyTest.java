package com.colak.concurrent.completablefuture.exceptionally;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class ExceptionallyTest {

    public static void main() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (true) {
                throw new RuntimeException("Something went wrong!");
            }
            return "Hello";
        }).exceptionally(ex -> "Recovered from: " + ex.getMessage());
        try {
            System.out.println(future.get()); // Prints "Recovered from: Something went wrong!"
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
