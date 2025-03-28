package com.colak.concurrent.structuredtaskscope.failfast;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

// See <a href="https://medium.com/@phoenixrising_93140/what-is-structured-concurrency-java-21-6134374696be">...</a>
// This test throws an exception
@Slf4j
public class StructuredTaskScopeTest {

    public static void main() throws Exception {
        StructuredTaskScopeTest structuredTaskScopeTest = new StructuredTaskScopeTest();
        log.info(structuredTaskScopeTest.failFast());
    }

    private String failFast() throws InterruptedException, ExecutionException {
        // ShutdownOnFailure policy means that if any of the subtasks fail within the scope ,
        // then all the subtasks are cancelled and control returns to the parent.
        // You can think of it like a short circuit operation policy.
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            StructuredTaskScope.Subtask<String> respA = scope.fork(this::taskThatErrorsOut);
            StructuredTaskScope.Subtask<String> respB = scope.fork(this::justAnotherLongRunningTask);
            scope.join();
            scope.throwIfFailed();
            return respA.get() + " " + respB.get();
        }
    }

    private String taskThatErrorsOut() throws InterruptedException {
        Thread.sleep(5000);
        throw new RuntimeException("task fails and we do not know why!");
    }

    private String justAnotherLongRunningTask() throws InterruptedException {
        int i = 0;
        while (i < 10) {
            Thread.sleep(2000);
            System.out.println("Doing fancy stuff  ...... ");
            i++;
        }
        System.out.println("Where is mama?");
        return "Task completed successfully , I win";
    }
}
