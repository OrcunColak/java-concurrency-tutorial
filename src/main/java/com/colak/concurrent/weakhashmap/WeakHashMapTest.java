package com.colak.concurrent.weakhashmap;

import lombok.extern.slf4j.Slf4j;

import java.util.WeakHashMap;

/**
 * See <a href="https://medium.com/@bubu.tripathy/clean-code-avoid-leaking-references-4bdce8d6c18b">...</a>
 */
@Slf4j
class WeakHashMapTest {

    private record Foo(String data) {
    }

    public static void main() {
        // Creating a WeakHashMap with weak keys
        WeakHashMap<Object, Foo> weakHashMap = new WeakHashMap<>();

        // Creating an object with associated data
        Object keyObject = new Object();
        Foo foo = new Foo("Some additional data");

        // Adding the object and associated data to the WeakHashMap
        weakHashMap.put(keyObject, foo);

        // Retrieving the associated data
        Foo retrievedData = weakHashMap.get(keyObject);

        // Outputting the associated data (it should be available)
        log.info("Associated Data: {}", retrievedData.data());

        // Allowing the keyObject to be eligible for garbage collection
        keyObject = null;

        // Simulating a garbage collection cycle
        System.gc();

        // Attempting to retrieve the associated data after keyObject is collected (it should be null)
        retrievedData = weakHashMap.get(keyObject);
        log.info("Associated Data after keyObject is collected: {}", retrievedData);
    }
}
