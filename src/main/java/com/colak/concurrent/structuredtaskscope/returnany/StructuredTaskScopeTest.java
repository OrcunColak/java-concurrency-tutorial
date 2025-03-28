package com.colak.concurrent.structuredtaskscope.returnany;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;


// See <a href="https://medium.com/@phoenixrising_93140/what-is-structured-concurrency-java-21-6134374696be">...</a>
@Slf4j
class StructuredTaskScopeTest {

    public static void main() throws Exception {
        StructuredTaskScopeTest structuredTaskScopeTest = new StructuredTaskScopeTest();
        log.info(structuredTaskScopeTest.returnAny());
    }

    private String returnAny() throws InterruptedException, ExecutionException {
        // ShutdownOnSuccess policy is the counterbalance to the ShutdownOnFailure policy, meaning that if any of the
        // subtasks succeed then all subtasks are cancelled and control returns to the parent.
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
            // Even though the taskThatErrorsOut is still failing
            // the parent task is satisfied that any of the subtasks has succeeded, and it processes that result.
            scope.fork(this::taskThatErrorsOut);
            scope.fork(this::justAnotherLongRunningTask);
            return scope.join().result();
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
