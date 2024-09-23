package com.colak.concurrent.reference.softrefence;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.SoftReference;

/**
 * See <a href="https://medium.com/nerd-for-tech/jvm-memory-management-part-2-46e03d546b8e">...</a>
 * <p>
 * The difference between WeakReference and SoftReference is that the Garbage collector can collect an object if only
 * weak references are pointing to it i.e., a weak reference is eagerly collected.
 * <p>
 * On the other hand, objects with Soft Reference are only collected when the JVM absolutely needs memory.
 * <p>
 * A phantom reference is a type of reference that allows you to monitor an object’s finalization (cleanup) by the garbage collector,
 * but it does not provide direct access to the referenced object.
 */
@Slf4j
class SoftReferenceTest {

    public static void main(String[] args) {
        SoftReference<String> softReference = new SoftReference<>(new String("I am soft"));

        String retrievedString = softReference.get();

        if (retrievedString != null) {
            log.info("Retrieved string: {}", retrievedString);
        } else {
            log.info("The referenced object has been garbage collected.");
        }
    }
}
