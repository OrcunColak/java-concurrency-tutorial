package com.colak.concurrent.completablefuture.resultandexception.whencomplete;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

// See href="https://medium.com/@suveshagnihotri/asynchronous-programming-in-java-a612281c9d2f

@Slf4j
class WhenCompleteExceptionTest {

    public static void main() throws ExecutionException, InterruptedException {
        // Fetch Quotation
        CompletableFuture<String> quotation = CompletableFuture.supplyAsync(() -> {
            // commonPool-worker-1
            log.info("quotation");
            return "getQuotation()";
        });
        // Send Email
        CompletableFuture<String> email = quotation.thenApply(str -> {
            // commonPool-worker-1
            sleep();
            log.info("email");
            return str + "email(quotation)";
        });
        // DB Operation
        CompletableFuture<String> writeToDb = email.thenApply(_ -> {
            // commonPool-worker-1
            sleep();
            log.info("writeToDB");
            throw new RuntimeException("writeToDB(emailInfo) exception");
        });
        try {
            String result = writeToDb.whenComplete((str, exception) -> {
                if (exception != null) {
                    log.error(exception.getMessage());
                } else {
                    log.info("str : {}", str);
                }
            }).join();
            log.info("Result : {}", result);
        } catch (Exception ignored) {
        }
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
