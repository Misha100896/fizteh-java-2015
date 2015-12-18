package ru.fizteh.fivt.students.threads;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;
import java.util.Random;

public class ThreadsRollcall {

    private static CyclicBarrier threadsStarted;
    private static CyclicBarrier threadsCompleted;
    private static volatile boolean allThreadsReady = true;
    private static final int RANDOM_BORDER = 10;

    private static class RollcallRunner extends Thread {
        private Random generator = new Random();

        @Override
        public final void run() {
            try {
                while (true) {
                    threadsStarted.await();
                    int randomSeed = generator.nextInt(RANDOM_BORDER);
                    if (randomSeed <= 1) {
                        allThreadsReady = false;
                        System.out.println("No");
                    } else {
                        System.out.println("Yes");
                    }
                    threadsCompleted.await();
                }
            } catch (InterruptedException | BrokenBarrierException exception) {
                System.out.println("Something went wrong " + exception.toString());
                System.out.println(exception.getMessage());
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        int countThreads = 0;
        try {
            countThreads = Integer.parseInt(args[0]);
        } catch (Exception exception) {
            System.out.println("Wrong input arguments" + exception.toString());
            System.exit(1);
        }

        threadsStarted = new CyclicBarrier(countThreads + 1);
        threadsCompleted = new CyclicBarrier(countThreads + 1);

        for (int i = 0; i < countThreads; i++) {
            RollcallRunner thread = new RollcallRunner();
            thread.start();
        }

        while (true) {
            allThreadsReady = true;
            System.out.println("Are you ready?");
            try {
                threadsStarted.await();
                threadsStarted.reset();
                threadsCompleted.await();
                threadsCompleted.reset();
            } catch (InterruptedException | BrokenBarrierException exception) {
                exception.printStackTrace();
                System.exit(1);
            }
            if (allThreadsReady) {
                System.exit(0);
            }
        }
    }
}