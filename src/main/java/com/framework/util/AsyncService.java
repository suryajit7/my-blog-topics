package com.framework.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class AsyncService {

    private final int DELAY = 1000;
    private final int INIT_DELAY = 2000;

    private AtomicLong value = new AtomicLong(0);
    private Executor executor = Executors.newFixedThreadPool(4);
    private volatile boolean initialized = false;

    public void initialize() {
        executor.execute(() -> {
            sleep(INIT_DELAY);
            initialized = true;
        });
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void addValue(long val) {
        throwIfNotInitialized();
        executor.execute(() -> {
            sleep(DELAY);
            value.addAndGet(val);
        });
    }

    public long getValue() {
        throwIfNotInitialized();
        return value.longValue();
    }

    private void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }

    private void throwIfNotInitialized() {
        if (!initialized) {
            throw new IllegalStateException("Service is not initialized");
        }
    }


}
