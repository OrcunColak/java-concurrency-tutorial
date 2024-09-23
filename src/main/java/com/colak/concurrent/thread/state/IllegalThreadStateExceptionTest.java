package com.colak.concurrent.thread.state;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

// See https://webcache.googleusercontent.com/search?q=cache:https://medium.com/@haiou-a/java-what-happens-when-a-thread-calls-start-twice-7b85ddd22c05

// All exceptions related to thread state throws IllegalThreadStateException
@Slf4j
@UtilityClass
public class IllegalThreadStateExceptionTest {

    public static void main() throws InterruptedException {
        doubleStart();
        startOnTerminatedThread();
    }


    private static void doubleStart() {
        Thread thread = new Thread(() -> {
        });
        // Thread-0:NEW
        log.info(" {} : {}", thread.getName(), thread.getState());
        // First call to start
        thread.start();

        // Thread-0:RUNNABLE
        log.info(" {} : {}", thread.getName(), thread.getState());

        try {
            // Second call to start
            thread.start();
        } catch (IllegalThreadStateException exception) {
            log.info("Exception : ", exception);
        }
    }

    private static void startOnTerminatedThread() throws InterruptedException {
        Thread thread = new Thread(() -> {
        });
        thread.start();

        TimeUnit.SECONDS.sleep(1);

        // Thread-0:TERMINATED
        log.info(" {} : {}", thread.getName(), thread.getState());

        try {
            // Second call to start
            thread.start();
        } catch (IllegalThreadStateException exception) {
            log.info("Exception : ", exception);
        }
    }
}
