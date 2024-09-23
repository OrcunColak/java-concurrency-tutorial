package com.colak.concurrent.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
class ConditionTest {

    private final ReentrantLock lock = new ReentrantLock(true);
    private int balance = 0;
    private final Condition transactionCondition = lock.newCondition();

    public static void main() throws Exception {
        ConditionTest conditionTest = new ConditionTest();
        conditionTest.testLock();
    }

    private void testLock() throws InterruptedException {
        Thread wifeThread = new Thread(() -> {
            for (int index = 0; index < 100; index++) {
                log.info("Withdraw balance : {}", withdraw(1));
            }
        });

        Thread husbandThread = new Thread(() -> {
            for (int index = 0; index < 100; index++) {
                log.info("Deposit balance : {}", deposit(1));
            }
        });

        wifeThread.start();
        husbandThread.start();

        // Wait for both threads to finish
        wifeThread.join();
        husbandThread.join();
    }

    public int withdraw(int value) {
        lock.lock();
        try {
            while (balance == 0) {
                log.info("Wife waiting for sufficient funds. Current balance: {}", balance);
                transactionCondition.await();
            }
            balance -= value;
            // Signal for any waiting threads
            transactionCondition.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Preserve interrupted status
            log.error("Wife thread interrupted while waiting.", e);
        } finally {
            lock.unlock();
        }
        return balance;
    }

    public int deposit(int value) {
        lock.lock();
        try {
            while (balance > 90) {
                log.info("Husband waiting for room in the account. Current balance: {}", balance);
                transactionCondition.await();
            }
            balance += value;
            // Signal for any waiting threads
            transactionCondition.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Preserve interrupted status
            log.error("Husband thread interrupted while waiting.", e);
        } finally {
            lock.unlock();
        }
        return balance;
    }
}
