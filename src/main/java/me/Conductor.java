package me;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Conductor {

    private static final Logger log = LogManager.getLogger(Conductor.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    // Shutdown the executor 3 sec after submitting tasks
    // If worker doesn't halt on interruption, the task goes on until its end
    public void createTask() {

        Future<?> future = executorService.submit(new Worker("W1"));
        executorService.submit(new Worker("W2"));
        log.info("Task launched");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdownNow();
        log.info("executorService.shutdownNow() done");

//        executorService.shutdown();
//        try {
//            boolean terminatedBeforeTimeout = executorService.awaitTermination(1, TimeUnit.HOURS);
//            System.out.println(terminatedBeforeTimeout);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}
