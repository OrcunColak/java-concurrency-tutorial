package com.colak.concurrent.semaphore;

import java.util.concurrent.Semaphore;

class SemaphoreTest {

    private final Semaphore semaphore = new Semaphore(3); // Allows 3 permits

    public static void main(String[] args) {
        SemaphoreTest semaphoreTest = new SemaphoreTest();
        semaphoreTest.accessResource();
    }

    private void accessResource() {
        try {
            semaphore.acquire();
            // Access the resource
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }


}
