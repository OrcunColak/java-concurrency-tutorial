package com.colak.concurrent.thread.platform.percorescheduler;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class PerCoreSchedulerTest {
    public static void main(String[] args) throws Exception {
        int virtualThreadCount = 20;
        PerCoreScheduler perCoreScheduler = new PerCoreScheduler(virtualThreadCount);
        log.info("perCoreScheduler created");

        Thread.sleep(100);

        for (int index = 0; index < 100; index++) {
            int virtualThreadIndex = index % virtualThreadCount;

            // Add to virtual thread queue
            perCoreScheduler.execute(virtualThreadIndex, () -> {
                log.info(" start sleep");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                log.info(" finished sleep");
            });
        }
    }
}
