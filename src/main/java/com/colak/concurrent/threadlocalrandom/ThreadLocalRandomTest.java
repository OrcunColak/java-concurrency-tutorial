package com.colak.concurrent.threadlocalrandom;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

// See https://stackoverflow.com/questions/71329830/why-do-i-always-get-the-same-result-when-using-a-threadlocalrandom-class-member
// This example shows that we should not store ThreadLocalRandom as a variable.
// When we run the main method we are always getting the same values
class ThreadLocalRandomTest {

    public static void main() throws Exception {
        var rnd = ThreadLocalRandom.current();

        try (var executor = Executors.newSingleThreadExecutor()) {
            for (int i = 0; i < 50; i++) {
                executor.submit(() -> System.out.println("Value: " + rnd.nextInt(1000, 10000)));
            }
        }

    }
}
