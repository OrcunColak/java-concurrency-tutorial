package com.colak.concurrent.phaser;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;

@Slf4j
public class PhaserTest2 {
  
    public static void main() {
        Phaser phaser = new Phaser(1); // Register main thread
      
        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName() + " started phase 1");
            phaser.arriveAndAwaitAdvance(); // Wait for others
            System.out.println(Thread.currentThread().getName() + " started phase 2");
        };

        new Thread(task).start();
        new Thread(task).start();

        phaser.arriveAndDeregister(); // Main thread deregisters
    }
}
