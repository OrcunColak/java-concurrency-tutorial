package com.colak.concurrent.executorservice;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
class SingleThreadExecutorTest {

    public static void main() throws ExecutionException, InterruptedException {
      
       try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
          Set<Callable<String>> callables = new HashSet<>();
            callables.add(() -> "Task 1");
            callables.add(() -> "Task 2");
            String result = executor.invokeAny(callables);
            log.info("Result: {}", result);
       }
    }
}
