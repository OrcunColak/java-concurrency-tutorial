# Executors.newCachedThreadPool()
If you have many short-lived tasks that can be executed concurrently, a CachedThreadPool can be a good choice.
Tasks are not queued in a CachedThreadPool. Instead, they are immediately executed by an available thread or a new thread is created if no threads are available.
