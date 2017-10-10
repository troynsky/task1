package com.provectus;

import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class SolutionProcessor implements Callable<BigDecimal> {

    private BlockingQueue<Solution> jobQueue;

    private volatile boolean keepProcessing;

    public SolutionProcessor(BlockingQueue<Solution> queue) {
        jobQueue = queue;
        keepProcessing = true;
    }

    public void cancelExecution() {
        this.keepProcessing = false;
    }

    public BigDecimal call() throws Exception {
        while (keepProcessing || !jobQueue.isEmpty()) {
            Solution j = jobQueue.poll();

            if (j != null) {
                return j.calculatePi(0);
            }
        }
        return null;
    }
}
