package com.colak.concurrent.referencequeue;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

@Slf4j
class ReferenceQueueTest {

    public static void main( String args[] ) throws InterruptedException {
        // Note, if we create the string using String literal, it will be stored in the Constant String Pool, not in the heap.
        String str = new String("Brand New String");

        // Create a reference queue typed on Integer
        ReferenceQueue<String> q = new ReferenceQueue<>();

        // Create the weak reference and pass the queue as a param
        WeakReference<String> wrappedInt = new WeakReference<>(str, q);

        str = null;

        // Try invoking the GC, but no guarantees it'll run
        Runtime.getRuntime().gc();

        // Prints the wrapped integer.
        log.info(wrappedInt.get());

        // Check if the queue has any item in it. It should return
        // null since no GC has been performed
        log.info(q.poll() == null ? "queue is empty" : "queue has an element");
    }
}
