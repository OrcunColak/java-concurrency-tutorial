package com.colak.concurrent.scheduledthreadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
class ScheduledThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        try (ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3)) {

            scheduledExecutorService.scheduleAtFixedRate(() -> log.info("Running"), 0, 1, TimeUnit.SECONDS);
            scheduledExecutorService.scheduleAtFixedRate(() -> log.info("Running"), 0, 1, TimeUnit.SECONDS);
            scheduledExecutorService.scheduleAtFixedRate(() -> log.info("Running"), 0, 1, TimeUnit.SECONDS);

            TimeUnit.SECONDS.sleep(30);
        }

    }
}
