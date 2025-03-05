package com.colak.concurrent.thread.platform.percorescheduler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class MyVirtualThreadTask implements Runnable {
    final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1024);

    void schedule(Runnable runnable) {
        queue.add(runnable);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Runnable runnable = queue.take();
                runnable.run();

                // this is to ensure that we release control of the virtual thread to prevent
                // that 1 virtual thread will hog the carrier thread.
                Thread.yield();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}