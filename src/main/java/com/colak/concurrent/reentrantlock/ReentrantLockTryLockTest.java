package com.colak.concurrent.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * At the end of this test the balance does not have to be 0 because wife may skip locking when husband is depositing
 */
@Slf4j
class ReentrantLockTryLockTest {

    private final ReentrantLock lock = new ReentrantLock(true);
    private int balance = 100;

    public static void main() throws InterruptedException {
        ReentrantLockTryLockTest readWriteLockTest = new ReentrantLockTryLockTest();
        readWriteLockTest.testLock();
    }

    private void testLock() throws InterruptedException {
        Thread wifeThread = new Thread(() -> {
            for (int index = 0; index < 100; index++) {
                log.info("Withdraw balance: {}", withdraw(2));
            }
        });
        wifeThread.start();

        Thread husbandThread = new Thread(() -> {
            for (int index = 0; index < 100; index++) {
                log.info("Deposit balance : {}", deposit(1));
            }
        });
        husbandThread.start();

        husbandThread.join();
        wifeThread.join();
    }

    private int withdraw(int value) {
        if (lock.tryLock()) {
            try {
                if (balance >= value) {
                    balance = balance - value;
                }
            } finally {
                // read lock released
                lock.unlock();
            }
        }
        return balance;
    }

    public int deposit(int value) {
        if (lock.tryLock()) {
            try {
                balance += value;
            } finally {
                lock.unlock();
            }
        }
        return balance;
    }
}
