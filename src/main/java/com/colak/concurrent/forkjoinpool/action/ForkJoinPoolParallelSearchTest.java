package com.colak.concurrent.forkjoinpool.action;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@Slf4j
class ForkJoinPoolParallelSearchTest {

    public static void main() {
        int[] array = new int[100_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        try (ForkJoinPool pool = new ForkJoinPool()) {
            int index = pool.invoke(new SearchTask(array, 0, array.length, 42));
            log.info("Index of target: {}", index);
        }
    }

    private static class SearchTask extends RecursiveTask<Integer> {
        private final int[] array;
        private final int start;
        private final int end;
        private final int target;
        private static final int THRESHOLD = 10_000;

        public SearchTask(int[] array, int start, int end, int target) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.target = target;
        }

        @Override
        protected Integer compute() {
            if (end - start <= THRESHOLD) {
                for (int i = start; i < end; i++) {
                    if (array[i] == target) {
                        return i;
                    }
                }
                return -1;
            } else {
                int mid = (start + end) / 2;
                SearchTask leftTask = new SearchTask(array, start, mid, target);
                SearchTask rightTask = new SearchTask(array, mid, end, target);
                leftTask.fork();

                int rightResult = rightTask.compute();
                int leftResult = leftTask.join();
                return leftResult != -1 ? leftResult : rightResult;
            }
        }
    }

}
