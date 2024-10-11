package com.colak.concurrent.threadgroup;

import lombok.extern.slf4j.Slf4j;

// What happens when the priority of a thread is different from that of its thread group?
// If the priority of a thread is greater than the priority of the thread group it is in, then that thread’s
// priority will be invalidated and replaced by the maximum priority of the thread group.
@Slf4j
class ThreadGroupAndThreadPriorityTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group = new ThreadGroup("MyThreadGroup");
        group.setMaxPriority(Thread.NORM_PRIORITY + 1); // 6

        Thread thread1 = new Thread(group, () -> {
            log.info("Thread 1");
        });
        thread1.setPriority(Thread.NORM_PRIORITY + 3); // 8
        Thread thread2 = new Thread(group, () -> {
            log.info("Thread 2");
        });
        thread2.setPriority(Thread.NORM_PRIORITY + 3); // 8

        thread1.start();
        thread2.start();


        // ThreadGroup 1 Priority : 6
        // Thread 1 Priority : 6
        // Thread 2 Priority : 6
        log.info("ThreadGroup 1 Priority : {}", group.getMaxPriority());
        log.info("Thread 1 Priority : {}", thread1.getPriority());
        log.info("Thread 2 Priority : {}", thread2.getPriority());
        thread1.join();
        thread2.join();

    }
}
