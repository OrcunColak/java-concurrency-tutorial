package com.colak.concurrent.cyclicbarrier;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// See https://itnext.io/implementing-a-multi-thread-file-downloader-using-java-synchronization-utilities-10a097feb47c
@Slf4j
@UtilityClass
public class CyclicBarrierTest {

    private static final int NUMBER_OF_PARTS = 4;
    private static final long TIMEOUT_MILLIS = 8_000;

    private static final SecureRandom random = new SecureRandom();

    public static void main() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_PARTS)) {
            CyclicBarrier barrier = new CyclicBarrier(NUMBER_OF_PARTS + 1, () -> {
                // This task will be executed once all threads reach the barrier
                combineParts();
                log.info("File download complete.");
            });

            for (int i = 1; i <= NUMBER_OF_PARTS; i++) {
                executorService.submit(new DownloadTask(i, barrier));
            }

            try {
                barrier.await(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | BrokenBarrierException | TimeoutException exception) {
                log.error("Error occurred: ", exception);
            } finally {
                executorService.shutdownNow();
            }
        }
    }

    private static void combineParts() {
        log.info("Combine Parts");
    }

    private record DownloadTask(int partId, CyclicBarrier barrier) implements Runnable {

        @Override
            public void run() {
                long randomSleep = random.nextLong(5_000);
                try {
                    TimeUnit.MILLISECONDS.sleep(randomSleep);

                    log.info("Download of part {}  is complete.", partId);

                    // Wait for other threads to reach the barrier
                    barrier.await();

                } catch (InterruptedException exception) {
                    log.info("Download of part {}  was interrupted.", partId);
                } catch (BrokenBarrierException exception) {
                    throw new RuntimeException(exception);
                }
            }
        }
}
