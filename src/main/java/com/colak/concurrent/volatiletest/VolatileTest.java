package com.colak.concurrent.volatiletest;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
@UtilityClass
class VolatileTest {

    private static volatile boolean flag = false;
    private static volatile int counter;

    public static void main() {
        Thread writerThread = new Thread(() -> {

            while (!flag) {
                counter++;
                sleep(1_000);
            }

        });

        Thread readerThread = new Thread(() -> {
            while (!flag) {
                // Busy-wait until the flag is true
                log.info("Counter {}", counter);
            }

        });

        readerThread.start();
        writerThread.start();

        sleep(10_000);

        flag = true;

        try {
            writerThread.join();
            readerThread.join();
        } catch (InterruptedException exception) {
            log.error("Exception : ", exception);
        }

        log.info("Test complete.");
    }

    private static void sleep(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException _) {

        }
    }
}

