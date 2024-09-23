package com.colak.concurrent.concurrenthashmap;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
class ConcurrentHashMapTest {

    public static void main() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.forEach((key, value) -> log.info("{} : {}", key, value));

        map.computeIfPresent("A", (key, value) -> value + 1);
        log.info("Updated value of A: {}", map.get("A"));
    }
}
