package com.colak.concurrent.blockingqueue;

import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
class BlockingQueueOfferTest {

    public static void main() {
        Queue<Integer> boundedQueue = new ArrayBlockingQueue<>(2);
        boundedQueue.offer(1);
        boundedQueue.offer(2);
        boolean added = boundedQueue.offer(3);  // Returns false instead of throwing an exception
        log.info("Was the element added? {}", added);  // Output: false
    }
}
