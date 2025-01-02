StampedLock, is optimized for read-heavy workloads. It provides:

Optimistic reads: Non-blocking reads when no write is happening.
Write lock: Exclusive access for modifications.
Read lock: Shared read access.

Drawback: Doesn’t support reentrancy.
