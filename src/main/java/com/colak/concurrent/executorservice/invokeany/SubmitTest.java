package com.colak.concurrent.executorservice.invokeany;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
class SubmitTest {

    public static void main() throws ExecutionException, InterruptedException {
        try (ExecutorService executor = Executors.newCachedThreadPool()) {
            Future<Integer> future = executor.submit(() -> {
                // This will throw an exception
                return 1 / 0;
            });

            try {
                future.get();
            } catch (ExecutionException | InterruptedException e) {
                System.err.println("Task failed: " + e.getMessage());
            }
        }
    }
}

