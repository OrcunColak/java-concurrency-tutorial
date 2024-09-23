package com.colak.concurrent.deadlock.two_class_initialization.badexamples;

/**
 * See <a href="https://blog.palantir.com/using-static-analysis-to-prevent-java-class-initialization-deadlocks-c2f31ca967d6">...</a>
 */
public class Example2 {

    static {
        new Sub();
    }

    static class Sub extends Example2 {
    }
}
