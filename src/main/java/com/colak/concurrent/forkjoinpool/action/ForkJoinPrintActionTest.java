package com.colak.concurrent.forkjoinpool.action;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

@Slf4j
public class ForkJoinPrintActionTest {

    public static void main() {
        try (ForkJoinPool pool = new ForkJoinPool()) {
            pool.invoke(new PrintTask(0, 100));
        }
    }

    private static class PrintTask extends RecursiveAction {
        private final int start;
        private final int end;
        private static final int THRESHOLD = 10;

        public PrintTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= THRESHOLD) {
                for (int i = start; i < end; i++) {
                    log.info("{} : {}", Thread.currentThread().getName(), i);
                }
            } else {
                int mid = (start + end) / 2;
                PrintTask leftTask = new PrintTask(start, mid);
                PrintTask rightTask = new PrintTask(mid, end);
                invokeAll(leftTask, rightTask);
            }
        }
    }

}
