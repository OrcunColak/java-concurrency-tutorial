package com.colak.concurrent.forkjoinpool.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@Slf4j
class ForkJoinPoolSumTaskTest {

    public static void main() {
        long[] array = new long[20_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        try (ForkJoinPool pool = new ForkJoinPool()) {
            long sum = pool.invoke(new SumTask(array, 0, array.length));
            log.info("Sum: {}", sum);
        }
    }

    private static class SumTask extends RecursiveTask<Long> {
        private final long[] array;
        private final int start;
        private final int end;
        private static final int THRESHOLD = 10_000;

        public SumTask(long[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            } else {
                int mid = (start + end) / 2;
                SumTask leftTask = new SumTask(array, start, mid);
                SumTask rightTask = new SumTask(array, mid, end);
                leftTask.fork();

                long rightResult = rightTask.compute();
                long leftResult = leftTask.join();
                return leftResult + rightResult;
            }
        }
    }


}
