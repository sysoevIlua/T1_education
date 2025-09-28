package org.sysoev;

import org.sysoev.task3.SelfThreadPool;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static volatile int primitiveInt = 0;
    private static volatile Integer wrapperInt  = 0;
    private static AtomicInteger atomicInt = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        SelfThreadPool pool = new SelfThreadPool(3);

        for (int i = 0; i < 6000; i++) {

            final int taskId = i;
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " выполняет задачу " + taskId);
                primitiveInt++;
                wrapperInt++;
                atomicInt.incrementAndGet();
            });
        }

        pool.shutdown();

        // Пробуем после shutdown добавить задачу
        try {
            pool.execute(() -> System.out.println("--------------"));
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        pool.awaitTermination();

        System.out.println("primitiveInt = "+ primitiveInt);
        System.out.println("wrapperInt = "+ wrapperInt);
        System.out.println("atomicInt = "+ atomicInt);
    }
}