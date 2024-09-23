package com.colak.concurrent.executorservice.invokeany;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// See https://dzone.com/articles/deep-dive-into-java-executorservice
@Slf4j
class InvokeAnyTest {

    public static void main() throws ExecutionException, InterruptedException {
        try (ExecutorService executor = Executors.newCachedThreadPool()) {
            Set<Callable<String>> callables = new HashSet<>();
            callables.add(() -> "Task 1");
            callables.add(() -> "Task 2");
            String result = executor.invokeAny(callables);
            log.info("Result: {}", result);
        }
    }
}
