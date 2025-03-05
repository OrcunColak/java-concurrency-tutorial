package com.colak.concurrent.thread.platform.percorescheduler;

import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public class PerCoreScheduler {
    private final MyCarrierThread carrierThread;
    private final Thread[] virtualThreads;
    private final MyVirtualThreadTask[] virtualThreadsTasks;
    private final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1024);

    public PerCoreScheduler(int virtualThreadCount) throws NoSuchFieldException, IllegalAccessException {
        carrierThread = new MyCarrierThread(queue);
        carrierThread.start();

        virtualThreads = new Thread[virtualThreadCount];
        virtualThreadsTasks = new MyVirtualThreadTask[virtualThreadCount];
        for (int index = 0; index < virtualThreadCount; index++) {
            Thread.Builder builder = Thread.ofVirtual();
            Field schedulerField = builder.getClass().getDeclaredField("scheduler");
            schedulerField.setAccessible(true);
            schedulerField.set(builder, new VirtualThreadContinuationScheduler());

            MyVirtualThreadTask task = new MyVirtualThreadTask();
            virtualThreadsTasks[index] = task;

            builder.name("virtualthread-" + index);

            Thread t = builder.start(task);
            virtualThreads[index] = t;
        }
    }

    public void execute(int virtualThreadIndex, Runnable command) {
        virtualThreadsTasks[virtualThreadIndex].schedule(command);
    }


    // responsible for executing continuations
    private class VirtualThreadContinuationScheduler implements Executor {
        @Override
        public void execute(Runnable command) {
            queue.add(command);
        }
    }

}
