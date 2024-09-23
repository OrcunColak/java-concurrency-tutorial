package com.colak.concurrent.forkjoinpool.task;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@Slf4j
@UtilityClass
public class ForkJoinPoolTest {

    public static void main() {
        try (ForkJoinPool pool = new ForkJoinPool()) {
            FibonacciTask task = new FibonacciTask(30);
            Integer fibonacci = pool.invoke(task);
            log.info("Result : {}", fibonacci);
        }
    }

    static class FibonacciTask extends RecursiveTask<Integer> {
        private final int n;

        FibonacciTask(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            FibonacciTask leftTask = new FibonacciTask(n - 1);
            FibonacciTask rightTask = new FibonacciTask(n - 2);
            leftTask.fork();

            Integer rightResult = rightTask.compute();
            int leftResult = leftTask.join();
            return leftResult + rightResult;
        }
    }
}
