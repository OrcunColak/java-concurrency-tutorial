package com.colak.concurrent.stampedlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.StampedLock;

// See https://medium.com/itnext/optimistic-locking-with-stampedlock-in-java-f2e4d5ba35cd?source=explore---------10-109--------------------9004247a_66a9_4df0_b411_e13f010b11fd-------15
@Slf4j
class SharedStoreTest {
    private final StampedLock lock = new StampedLock();
    private int sharedData = 0;

    public static void main() {
        SharedStoreTest store = new SharedStoreTest();

        // Creating a thread that will write data
        Thread writer = new Thread(() -> store.write(42), "Writer");

        // Creating a thread that will attempt to read data optimistically
        Thread reader = new Thread(() -> {
            int value = store.read();
            log.info(" final read value: {}", value);
        }, "Reader");

        // Starting the threads
        reader.start();
        writer.start();

        // Wait for threads to finish
        try {
            reader.join();
            writer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    // Writing data
    public void write(int newValue) {
        long stamp = lock.writeLock();
        try {
            sharedData = newValue;
            log.info(" wrote value: {}", newValue);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    // Reading data (optimistic read)
    public int read() {
        long stamp = lock.tryOptimisticRead();
        int currentValue = sharedData;

        log.info(" current value: {}", currentValue);

        try {
            // Simulate some delay to increase the chance of a "write" occurring during the read
            Thread.sleep(100);

            // Validate if the read was consistent
            if (lock.validate(stamp)) {
                log.info(" read value: {} optimistically", currentValue);
            } else {
                // Fallback to a read lock
                stamp = lock.readLock();
                currentValue = sharedData;
                log.info(" detected an inconsistent read. Value after fallback: {}", currentValue);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlockRead(stamp);
        }
        return currentValue;
    }
}
