package com.colak.concurrent.atomiclong;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
class AtomicLongTest {

    AtomicLong atomicLong = new AtomicLong();

    public static void main() {
        AtomicLongTest atomicLongTest = new AtomicLongTest();
        atomicLongTest.addAndGet();
    }

    private void addAndGet() {
        long value = atomicLong.addAndGet(100);
        log.info("Value after adding 100 : {}", value);
    }
}
