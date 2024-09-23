package com.colak.concurrent.completablefuture.thencompose;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

// See https://medium.com/@suveshagnihotri/asynchronous-programming-in-java-a612281c9d2f
// henCompose combines two CompletableFutures sequentially, where the second CompletableFuture is dependent on the result of the first.
@Slf4j
class ThenComposeTest {

    public static void main() throws ExecutionException, InterruptedException {
        // Fetch Quotation
        CompletableFuture<String> quotation = CompletableFuture.supplyAsync(() -> {
            log.info("quotation");
            return "getQuotation()";
        });
        // Send Email
        CompletableFuture<String> email = CompletableFuture.supplyAsync(() -> {
            log.info("email");
            return "email(quotation)";
        });

        // First execute quotation
        // Then execute email with the result of quotation
        String result = quotation.thenCompose(str1 -> email.thenApply(str2 -> {
            log.info("str1 : {} , str2 : {}", str1, str2);
            return str1 + str2;
        })).join();

        log.info("Result : {}", result);

    }

}
