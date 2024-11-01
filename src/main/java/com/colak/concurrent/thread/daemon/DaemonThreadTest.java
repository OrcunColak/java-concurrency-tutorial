package com.colak.concurrent.thread.daemon;

import java.util.concurrent.TimeUnit;

// A daemon thread in Java does not prevent the JVM from exiting, so it will terminate when all non-daemon (user) threads finish
// their execution or when the JVM is explicitly terminated (e.g., due to a call to System.exit() or because of a fatal error).
class DaemonThreadTest {

    public static void main() throws InterruptedException {
        // Create a daemon thread
        Thread thread = new Thread(() -> {
            // Infinite loop
            while (true) {
                System.out.println("Daemon thread is running...");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        TimeUnit.SECONDS.sleep(1);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("ShutdownHook is running...")));
        System.out.println("The main thread is about to finish execution");
    }
}
