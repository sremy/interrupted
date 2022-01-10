package me;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Worker implements Runnable {

    private final String workerId;

    private static final Logger log = LogManager.getLogger(Worker.class);

    public Worker(String workerId) {
        this.workerId = workerId;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
                int value = longCompute(i);
                if (value % 2 != 0) {
                    log.info(i + " -> " + value);
                }

                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
            }
        } finally {
            log.info("End of run()");
        }
    }

    private int compute(int i) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            log.warn("Sleep interrupted", e);
            log.info("Cause:" + e.getCause());
            Thread.currentThread().interrupt();
//            throw new RuntimeException("Interrupted on " + i);
        }
        if (i == 50) {
            if (workerId.equals("W1")) {
                throw new OutOfMemoryError("");
            }
        }
        return i * i;
    }

    private int longCompute(int n) {
        for (int i = 0; i < 20000; i++) {
            for (int j = 0; j < 20000; j++) {
                if ((long) n * i * j > 1000001000000L) {
                    return i * j;
                }
            }
        }
        return n * n;
    }
}
