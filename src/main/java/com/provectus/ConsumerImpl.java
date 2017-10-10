package com.provectus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class ConsumerImpl implements Consumer {

    private BlockingQueue<Solution> itemQueue = new LinkedBlockingQueue<Solution>();

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private List<SolutionProcessor> jobList = new LinkedList<SolutionProcessor>();

    private volatile boolean shutdownCalled = false;

    List<Future<BigDecimal>> result = Collections.synchronizedList(new ArrayList<>());


    public ConsumerImpl(int poolSize) {
        for (int i = 0; i < poolSize; i++) {
            SolutionProcessor jobThread =
                    new SolutionProcessor(itemQueue);

            jobList.add(jobThread);
            result.add(executorService.submit(jobThread));
        }
    }

    public boolean consume(Solution j) {
        if (!shutdownCalled) {
            try {
                itemQueue.put(j);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public void finishConsumption() {
        for (SolutionProcessor j : jobList) {
            j.cancelExecution();
        }

        executorService.shutdown();
    }

    public List<Future<BigDecimal>> getResult() {
        return result;
    }

}