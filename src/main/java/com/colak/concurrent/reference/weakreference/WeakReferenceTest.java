package com.colak.concurrent.reference.weakreference;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.WeakReference;

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
class WeakReferenceTest {

    public static void main(String[] args) {
        // Note, if we create the string using String literal, it will be stored in the Constant String Pool, not in the heap.
        // In that case, if case will be printed.
        String str = new String("Brand New String");
        WeakReference<String> myString = new WeakReference<>(str);
        str = null; // nulling the strong reference

        // Try invoking the GC, but no guarantees it'll run
        Runtime.getRuntime().gc();

        if (myString.get() != null) {
            log.info(myString.get());
        } else {
            log.info("String object has been cleared by the Garbage Collector.");
        }
    }
}
