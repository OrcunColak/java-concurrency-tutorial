package com.colak.concurrent.atomicstampedreference;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicStampedReference;

@Slf4j
@UtilityClass
class AtomicStampedReferenceTest {

    public static void main() throws InterruptedException {
        // Initial reference and stamp
        String initialRef = "Initial Value";
        int initialStamp = 0;

        // Create AtomicStampedReference
        AtomicStampedReference<String> atomicStampedRef = new AtomicStampedReference<>(initialRef, initialStamp);

        // Thread 1: Performs an update
        Thread thread1 = new Thread(() -> {
            int[] stampHolder = new int[1];
            String currentRef = atomicStampedRef.get(stampHolder);
            int currentStamp = stampHolder[0];

            log.info("Thread 1 - Current Ref: {} , Current Stamp: {}", currentRef, currentStamp);

            // Simulate some work
            try {
                Thread.sleep(100);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                log.error("Exception:", exception);
            }

            // Attempt to update the reference and stamp
            boolean isUpdated = atomicStampedRef.compareAndSet(currentRef, "Thread 1 Value", currentStamp, currentStamp + 1);
            log.info("Thread 1 - Was Update Successful? {}", isUpdated);
        });

        // Thread 2: Performs the ABA problem scenario
        Thread thread2 = new Thread(() -> {
            int[] stampHolder = new int[1];
            String currentRef = atomicStampedRef.get(stampHolder);
            int currentStamp = stampHolder[0];

            log.info("Thread 2 - Current Ref: {} , Current Stamp: {}", currentRef, currentStamp);

            // Simulate ABA problem
            atomicStampedRef.compareAndSet(currentRef, "Intermediate Value", currentStamp, currentStamp + 1);
            atomicStampedRef.compareAndSet("Intermediate Value", initialRef, currentStamp + 1, currentStamp + 2);

            log.info("Thread 2 - ABA Problem Created: Ref back to {}", atomicStampedRef.getReference());
        });

        // Start both threads
        thread1.start();
        thread2.start();

        // Wait for both threads to finish
        thread1.join();
        thread2.join();

        // Final state
        int[] finalStampHolder = new int[1];
        String finalRef = atomicStampedRef.get(finalStampHolder);
        int finalStamp = finalStampHolder[0];
        log.info("Final Ref: {} , Final Stamp: {}", finalRef, finalStamp);
    }
}

