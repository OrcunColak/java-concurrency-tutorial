package com.colak.concurrent.thread.uncaughtexceptionhandler;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class ThreadUncaughtExceptionHandlerTest {

    public static void main() {
        Thread thread = new Thread(() -> {
            throw new RuntimeException("Intentional Exception");
        });

        thread.setUncaughtExceptionHandler((t, e) -> {
            log.error("Uncaught exception in thread : {} : {}", t.getName(), e.getMessage());
        });

        thread.start();
    }
}
