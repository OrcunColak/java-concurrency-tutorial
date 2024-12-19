@Slf4j
class SubmitTest {

    public static void main() throws ExecutionException, InterruptedException {
        try (ExecutorService executor = Executors.newCachedThreadPool()) {
            Future<Integer> future = executor.submit(() -> {return 1 / 0; // This will throw an exception});
                                                            
          try {
            future.get();
          } catch (ExecutionException | InterruptedException e) {
            System.err.println("Task failed: " + e.getMessage());
          }
        }
    }
}
