# Configuring Virtual Thread Scheduler

**jdk.virtualThreadScheduler.parallelism **  
This property sets the number of platform threads available for scheduling virtual threads.  
By default, it equals the number of available processors.

**jdk.virtualThreadScheduler.maxPoolSize**  
This property defines the maximum number of platform threads available to the scheduler.   
Its default value is typically 256, but you can adjust it for your application's needs:

# synchronized

See https://medium.com/@phil_3582/java-virtual-threads-some-early-gotchas-to-look-out-for-f65df1bad0db
Any libraries used by virtual threads might use synchronized methods or synchronized blocks around long IO operations.

If so, this will pin the virtual threads to their carrier threads during the IO operations thereby limiting any
performance advantage.

You might be able to work around this but ultimately synchronized methods and blocks should be
replaced with ReentrantLock. This has already been done in the Java libraries but there are still thousands of third
party libraries which may have this problem.