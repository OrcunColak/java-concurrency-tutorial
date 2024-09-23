package com.colak.concurrent.reference.phantom;

import lombok.experimental.UtilityClass;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

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
@UtilityClass
class PhantomReferenceTest {

    public static void main() {
        ReferenceQueue<String> referenceQueue = new ReferenceQueue<>();
        PhantomReference<String> phantomReference = new PhantomReference<>(new String("I am phantom"), referenceQueue);
    }
}
