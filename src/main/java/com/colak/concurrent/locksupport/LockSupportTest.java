package com.colak.concurrent.locksupport;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport provides alternative for the deprecated method of thread suspend() and resume().
 * See https://medium.com/@avinashsoni9829/locks-in-java-part-3-locksupport-607d8766ed1a
 */
@Slf4j
public class LockSupportTest {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> counters = new ArrayList<>();
        final int[] count = {0};
        Thread workerThread = new Thread(() -> {
            // workerThread waits for signal from t2. It is like suspend()
            LockSupport.park();
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    counters.add(count[0]);
                    count[0]++;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        workerThread.start();

        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(2_500L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // unpark simply releases the permit and makes the thread available for further processing.
            // It is like resume()
            LockSupport.unpark(workerThread);
        });
        t2.start();

        TimeUnit.SECONDS.sleep(5);
        workerThread.interrupt();

        log.info("Counters : {}", counters);
    }
}
