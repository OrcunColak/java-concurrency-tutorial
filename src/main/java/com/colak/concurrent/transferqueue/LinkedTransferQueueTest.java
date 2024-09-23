package com.colak.concurrent.transferqueue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

@Slf4j
public class LinkedTransferQueueTest {

    // Shared TransferQueue between producers and consumers
    private static final TransferQueue<String> queue = new LinkedTransferQueue<>();

    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {

        // Start Consumer Threads
        Thread consumer1 = new Thread(new Consumer(), "Consumer-1");
        Thread consumer2 = new Thread(new Consumer(), "Consumer-2");
        consumer1.start();
        consumer2.start();

        // Start Producer Threads
        Thread producer1 = new Thread(new Producer("Message-1"), "Producer-1");
        Thread producer2 = new Thread(new Producer("Message-2"), "Producer-2");
        producer1.start();
        producer2.start();

        producer1.join();
        producer2.join();

        stop();

        consumer1.join();
        consumer2.join();
    }

    private static void stop() {
        running = false;
    }

    // Producer that uses the transfer method to transfer messages to the consumer
    static class Producer implements Runnable {
        private final String message;

        public Producer(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            try {
                log.info("{} is transferring: {}", Thread.currentThread().getName(), message);
                // Transfer the message and wait for a consumer to receive it
                queue.transfer(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Producer interrupted", e);
            }
        }
    }

    // Consumer that takes the messages from the queue
    static class Consumer implements Runnable {

        @Override
        public void run() {
            try {
                while (running) {
                    // Blocks until a message is available
                    String message = queue.take();
                    log.info("{} received: {}", Thread.currentThread().getName(), message);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Consumer interrupted", e);
            }
        }
    }
}
