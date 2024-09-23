package com.colak.concurrent.objectpool;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

// See https://blog.devgenius.io/implementing-a-rate-limiter-quickly-using-semaphore-in-java-2b344214f35a
@Slf4j
@UtilityClass
class ObjectPoolTest {

    static class ObjPool<T, R> {
        final List<T> pool;
        final Semaphore semaphore;

        ObjPool(int size, T t) {
            pool = new Vector<>();

            for (int i = 0; i < size; i++) {
                pool.add(t);
            }
            semaphore = new Semaphore(size);
        }

        // use the object from the object pool to call func
        R exec(Function<T, R> func) throws InterruptedException {
            T t = null;
            semaphore.acquire();
            try {
                t = pool.removeFirst();
                return func.apply(t);
            } finally {
                pool.add(t);
                semaphore.release();
            }
        }
    }

    public static void main() throws InterruptedException {
        // create an object pool
        ObjPool<Long, String> pool = new ObjPool<>(10, 2L);
        // acquire t from the object pool and then execute.
        pool.exec(t -> {
            System.out.println(t);
            return t.toString();
        });
    }
}
