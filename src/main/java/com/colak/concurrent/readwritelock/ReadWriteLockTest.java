package com.colak.concurrent.readwritelock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
class ReadWriteLockTest {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private int balance = 0;

    public static void main() throws InterruptedException {
        ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();
        readWriteLockTest.testLock();
    }

    private void testLock() throws InterruptedException {
        Thread wifeThread = new Thread(() -> {
            for (int index = 0; index < 100; index++) {
                log.info("Read : {}", readBalance());
            }
        });
        wifeThread.start();

        Thread husbandThread = new Thread(() -> {
            for (int index = 0; index < 100; index++) {
                log.info("Write : {}", deposit(1));
            }
        });
        husbandThread.start();

        husbandThread.join();
        wifeThread.join();
    }

    private int readBalance() {
        // Acquiring the read lock
        lock.readLock().lock();
        try {
            // Perform read operations on the shared resource
            return balance;
        } finally {
            // read lock released
            lock.readLock().unlock();
        }
    }

    public int deposit(int value) {
        // acquiring the write lock
        lock.writeLock().lock();
        try {
            // Perform write operations on the shared resource
            balance += value;
            return balance;
        } finally {
            // write lock released
            lock.writeLock().unlock();
        }
    }
}
