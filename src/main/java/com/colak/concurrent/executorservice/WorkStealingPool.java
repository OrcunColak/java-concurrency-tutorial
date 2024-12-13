import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
class SingleThreadExecutor {

    public static void main() throws ExecutionException, InterruptedException {
      
       try (ExecutorService executor = Executors.newWorkStealingPool()) {
          Set<Callable<String>> callables = new HashSet<>();
            callables.add(() -> "Task 1");
            callables.add(() -> "Task 2");
            String result = executor.invokeAny(callables);
            log.info("Result: {}", result);
       }
    }
}