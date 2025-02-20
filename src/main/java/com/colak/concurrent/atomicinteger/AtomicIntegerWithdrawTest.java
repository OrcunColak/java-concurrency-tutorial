package com.colak.concurrent.atomicinteger;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerWithdrawTest {

    public static void main() {

        Account account1 = new Account(1000);
        Account account2 = new Account(500);

        boolean success = account1.transferTo(account2, 200);
        System.out.println("Transfer successful: " + success);
        System.out.println("Account1 balance: " + account1.getBalance());
        System.out.println("Account2 balance: " + account2.getBalance());
    }

    public static class Account {
        private final AtomicInteger balance;

        public Account(int initialBalance) {
            this.balance = new AtomicInteger(initialBalance);
        }

        public int getBalance() {
            return balance.get();
        }

        // We need to use CAS because we want to check that account has sufficient funds
        public boolean withdraw(int amount) {
            if (amount <= 0) return false; // Invalid amount

            while (true) {
                int currentBalance = balance.get();

                // Insufficient funds
                if (currentBalance < amount) {
                    return false;
                }

                int newBalance = currentBalance - amount;

                if (balance.compareAndSet(currentBalance, newBalance)) {
                    // Withdrawal successful
                    return true;
                }
                // If CAS fails, retry
            }
        }

        public void deposit(int amount) {
            if (amount > 0) {
                balance.getAndAdd(amount); // Atomic addition
            }
        }

        public boolean transferTo(Account target, int amount) {
            if (withdraw(amount)) { // Withdraw first
                target.deposit(amount); // Then deposit
                return true;
            }
            return false; // Transfer failed due to insufficient funds
        }
    }
}
