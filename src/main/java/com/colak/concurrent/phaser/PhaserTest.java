package com.colak.concurrent.phaser;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;

// A more flexible synchronization barrier, allowing dynamic registration and deregistration of threads.

@Slf4j
class PhaserTest {

    private static final int N_TASKS = 3;

    public static void main() {

        Phaser phaser = new Phaser(1); // "1" to register the main thread
        for (int i = 0; i < N_TASKS; i++) {
            phaser.register();
            new Thread(new Task(phaser)).start();
        }
        phaser.arriveAndAwaitAdvance(); // Main thread waits for all tasks to complete
        System.out.println("All tasks completed.");
    }

    static class Task implements Runnable {
        private final Phaser phaser;

        public Task(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            log.info("Task executed by thread");
            phaser.arriveAndDeregister(); // Signal arrival and deregister
        }
    }


}
