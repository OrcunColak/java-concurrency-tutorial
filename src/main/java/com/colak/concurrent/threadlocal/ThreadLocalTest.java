package com.colak.concurrent.threadlocal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class ThreadLocalTest {

    public static void main(String[] args) {
        ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);
        threadLocal.set(42); // Set a thread-local variable
        int value = threadLocal.get();
        log.info("Value : {}", value);
    }
}
