package com.colak.concurrent.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


@Slf4j
class ReentrantLockLockInterruptiblyTest {

    private final ReentrantLock lock = new ReentrantLock(true);
    private int balance = 100;

    public static void main() throws InterruptedException {
        ReentrantLockLockInterruptiblyTest readWriteLockTest = new ReentrantLockLockInterruptiblyTest();
        readWriteLockTest.testLock();
    }

    private void testLock() throws InterruptedException {
        Thread wifeThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                log.info("Withdraw balance: {}", withdraw(2));
            }
            log.info("Wife thread has been interrupted");
        });
        wifeThread.start();

        Thread husbandThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                log.info("Deposit balance : {}", deposit(1));
            }
            log.info("Husband thread has been interrupted");
        });
        husbandThread.start();

        TimeUnit.MILLISECONDS.sleep(10);

        husbandThread.interrupt();
        husbandThread.join();

        wifeThread.interrupt();
        wifeThread.join();
    }

    private int withdraw(int value) {
        try {
            lock.lockInterruptibly();
            try {
                if (balance >= value) {
                    // Perform read operations on the shared resource
                    balance = balance - value;
                }
            } finally {
                // read lock released
                lock.unlock();
            }
        } catch (InterruptedException exception) {
            log.info("Wife thread has been interrupted");
            Thread.currentThread().interrupt();
        }
        return balance;
    }

    public int deposit(int value) {
        try {
            lock.lockInterruptibly();
            try {
                // Perform write operations on the shared resource
                balance += value;
            } finally {
                // write lock released
                lock.unlock();
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
        return balance;
    }
}
