package com.colak.concurrent.stampedlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.StampedLock;

@Slf4j
class StampedLockTest {

    private double x;
    private double y;
    private final StampedLock stampedLock = new StampedLock();

    public static void main() {
        StampedLockTest example = new StampedLockTest();
        example.move(1.0, 2.0);

        log.info("Distance: {}", example.distanceFromOrigin());
        log.info("Optimistic Distance: {}", example.distanceFromOriginOptimistic());
    }


    // Writing method
    private void move(double deltaX, double deltaY) {
        long stamp = stampedLock.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    // Reading method using read lock
    private double distanceFromOrigin() {
        long stamp = stampedLock.readLock();
        try {
            return Math.sqrt(x * x + y * y);
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    // Reading method using optimistic read lock
    private double distanceFromOriginOptimistic() {
        long stamp = stampedLock.tryOptimisticRead();

        double currentX = x;
        double currentY = y;

        if (!stampedLock.validate(stamp)) {
            stamp = stampedLock.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}
