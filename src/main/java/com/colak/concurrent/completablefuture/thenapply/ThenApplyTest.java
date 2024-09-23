package com.colak.concurrent.completablefuture.thenapply;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


// See href="https://medium.com/@suveshagnihotri/asynchronous-programming-in-java-a612281c9d2f
// thenApply to apply a function on the result of the previous CompletableFuture

@Slf4j
class ThenApplyTest {

    public static void main() throws ExecutionException, InterruptedException {
        // Fetch Quotation
        CompletableFuture<String> quotation = java.util.concurrent.CompletableFuture.supplyAsync(() -> {
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
        CompletableFuture<String> writeToDb = email.thenApply(str -> {
            // commonPool-worker-1
            sleep();
            log.info("writeToDB");
            return str + "writeToDB(emailInfo)";
        });
        writeToDb.thenAccept(str -> {
            // commonPool-worker-1
            sleep();
            log.info("Result {}", str);
        }).get();
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
