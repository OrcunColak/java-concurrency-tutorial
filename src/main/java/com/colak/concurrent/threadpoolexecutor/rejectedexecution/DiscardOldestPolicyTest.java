package com.colak.concurrent.threadpoolexecutor.rejectedexecution;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// Discards the oldest task without any exception
// See https://medium.com/@ankithahjpgowda/policies-of-threadpoolexecutor-in-java-75f22fd6f637
class DiscardOldestPolicyTest {

    public static void main(String[] args) {

        int corePoolSize = 1;
        int maxPoolSize = 5;
        long keepAliveTime = 0L;
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(5);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();

        try (ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, queue, handler)) {

            for (int i = 0; i < 16; i++) {
                final int cnt = i;
                threadPoolExecutor.execute(() -> {
                    try {
                        System.out.println("At time: " + LocalDateTime.now() + " task:" + cnt + " " + Thread.currentThread().getName() + " queue size:" + threadPoolExecutor.getQueue().size());
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }
    }
}
