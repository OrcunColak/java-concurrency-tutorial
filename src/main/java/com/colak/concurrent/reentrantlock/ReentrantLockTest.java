package com.colak.concurrent.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * At the end of this test the balance must be 0 because wife is withdrawing more than deposit
 */
@Slf4j
class ReentrantLockTest {

    private final ReentrantLock lock = new ReentrantLock(true);
    private int balance = 100;

    public static void main() throws InterruptedException {
        ReentrantLockTest readWriteLockTest = new ReentrantLockTest();
        readWriteLockTest.testLock();
    }

    private void testLock() throws InterruptedException {
        Thread wifeThread = new Thread(() -> {
            for (int index = 0; index < 100; index++) {
                log.info("Withdraw balance : {}", withdraw(2));
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
        lock.lock();
        try {
            if (balance >= value) {
                balance = balance - value;
                return balance;
            } else {
                return balance;
            }
        } finally {
            lock.unlock();
        }
    }

    public int deposit(int value) {
        lock.lock();
        try {
            balance += value;
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
