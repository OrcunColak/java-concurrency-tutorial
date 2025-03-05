package com.colak.concurrent.thread.virtual;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// See https://satishkumarandey.medium.com/embracing-virtual-threads-in-java-unlocking-simplicity-and-scalability-0e3050341946
// Thread.isVirtual() will be used to check the virtual thread.
// The Thread.setPriority(int) method does not affect virtual threads, which always have a priority of Thread.NORM_PRIORITY.
// The Thread.setDaemon(boolean) the method does not affect virtual threads, which are always daemon threads.
// Thread.getAllStackTraces() now returns a map of all platform threads rather than a map of all threads.
// The -XX:+PreserveFramePointer flag has a drastic negative impact on virtual thread performance.
@Slf4j
class VirtualThreadTest {

    public static void main() throws Exception {
        createVirtualThread();
        createVirtualThreadWithBuilder();

        createVirtualThreadWithExecutors();
        createVirtualThreadWithExecutors2();
    }

    private static void createVirtualThread() throws InterruptedException {
        // Start thread directly
        Thread thread1 = Thread.ofVirtual().start(() -> log.info("Hello"));
        thread1.join();

        Runnable task = () -> log.info("Running virtual thread");
        // Start virtual thread in a convenient way
        Thread thread2 = Thread.startVirtualThread(task);
        thread2.join();

        // Create un-started virtual thread named virtual-1
        Thread thread3 = Thread.ofVirtual().name("virtual-1").unstarted(task);
        thread3.start();
    }

    private static void createVirtualThreadWithBuilder() throws InterruptedException {
        Thread.Builder builder = Thread.ofVirtual().name("virtual-1");
        Runnable task = () -> log.info("Running virtual thread");
        Thread t = builder.start(task);
        log.info("Thread t name: " + t.getName());
        t.join();

    }

    private static void createVirtualThreadWithExecutors() throws ExecutionException, InterruptedException {
        try (ExecutorService myExecutor = Executors.newVirtualThreadPerTaskExecutor()) {

            Future<?> future = myExecutor.submit(() -> log.info("Running thread"));
            future.get();
            log.info("Task completed");
        }
    }

    private static void createVirtualThreadWithExecutors2() throws ExecutionException, InterruptedException {
        // Here we are not using a ThreadFactory to give virtual threads a name
        try (ExecutorService myExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<?> future = myExecutor.submit(() -> log.info("Running thread"));
            future.get();
            log.info("Task completed");
        }
    }
}
