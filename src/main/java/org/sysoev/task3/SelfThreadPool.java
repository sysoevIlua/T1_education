package org.sysoev.task3;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.Executor;

public class SelfThreadPool implements Executor {

    private final LinkedList<Runnable> listOfTasks = new LinkedList<>();
    private final Set<Worker> workers = new HashSet<>();
    private volatile boolean isShutdown = false;

    public SelfThreadPool(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity must be a positive integer");
        for (int i = 0; i < capacity; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            new Thread(worker, "Worker - " + i).start();
        }
    }

    @Override
    public void execute(Runnable command) {
        if (command == null)
            throw new IllegalArgumentException("command cannot be null");

        synchronized (listOfTasks) {
            if (isShutdown)
                throw new IllegalStateException("SelfThreadPool is already shutdown");

            listOfTasks.add(command);
            listOfTasks.notify();
        }
    }

    public void shutdown() {
        isShutdown = true;
    }

    public void awaitTermination() throws InterruptedException {
        while (true) {
            synchronized (listOfTasks) {
                if (listOfTasks.isEmpty() && isShutdown) {
                    break;
                }
            }
            Thread.sleep(100);
        }
    }

    private final class Worker implements Runnable {
        @Override
        public void run() {
            while (true) {
                Runnable task;

                synchronized (listOfTasks) {
                    while (listOfTasks.isEmpty() && !isShutdown) {
                        try {
                            listOfTasks.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    if (listOfTasks.isEmpty() && isShutdown) {
                        return;
                    }

                    task = listOfTasks.removeFirst();
                }
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}