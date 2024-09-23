package com.colak.concurrent.atomicmarkablereference;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicMarkableReference;

@Slf4j
@UtilityClass
class AtomicMarkableReferenceTest {

    public static void main() throws InterruptedException {
        // Initial reference and mark
        String initialRef = "Initial Value";
        boolean initialMark = false;

        // Create AtomicMarkableReference
        AtomicMarkableReference<String> atomicMarkableReference = new AtomicMarkableReference<>(initialRef, initialMark);

        // Thread 1: Attempts to update the reference and mark
        Thread thread1 = new Thread(() -> {
            boolean[] markHolder = new boolean[1];
            String currentRef = atomicMarkableReference.get(markHolder);
            boolean currentMark = markHolder[0];

            log.info("Thread 1 - Current Ref: {}, Current Mark: {}", currentRef, currentMark);

            // Simulate some work
            try {
                Thread.sleep(100);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                log.error("Thread 1 interrupted", exception);
            }

            // Attempt to update the reference and mark
            boolean success = atomicMarkableReference.compareAndSet(currentRef, "Thread 1 Value", currentMark, true);
            log.info("Thread 1 - Was Update Successful? {}", success);
        });

        // Thread 2: Performs an update on the reference and mark
        Thread thread2 = new Thread(() -> {
            boolean[] markHolder = new boolean[1];
            String currentRef = atomicMarkableReference.get(markHolder);
            boolean currentMark = markHolder[0];

            log.info("Thread 2 - Current Ref: {}, Current Mark: {}", currentRef, currentMark);

            // Simulate some work
            atomicMarkableReference.compareAndSet(currentRef, "Thread 2 Value", currentMark, true);

            log.info("Thread 2 - Updated Ref: {}, Updated Mark: {}", atomicMarkableReference.getReference(), atomicMarkableReference.isMarked());
        });

        // Start both threads
        thread1.start();
        thread2.start();

        // Wait for both threads to finish
        thread1.join();
        thread2.join();

        // Final state
        boolean[] finalMarkHolder = new boolean[1];
        String finalRef = atomicMarkableReference.get(finalMarkHolder);
        boolean finalMark = finalMarkHolder[0];
        log.info("Final Ref: {}, Final Mark: {}", finalRef, finalMark);
    }

}
