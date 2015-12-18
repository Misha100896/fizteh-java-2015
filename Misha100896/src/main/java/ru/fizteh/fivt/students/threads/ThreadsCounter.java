package ru.fizteh.fivt.students.threads;

public class ThreadsCounter {
    private static Object monitor = new Object();
    private static volatile int activeThread = 0;

    public static class CounterRunner extends Thread {

        private int threadID, countThreads;

        CounterRunner(int threadNumber, int totalThreads) {
            threadID = threadNumber;
            countThreads = totalThreads;
        }

        @Override
        public final void run() {
            try {
                while (true) {
                    synchronized (monitor) {
                        if (threadID == activeThread) {
                            System.out.println("Thread-" + (threadID + 1));
                            activeThread++;
                            activeThread %= countThreads;
                            monitor.notifyAll();
                        } else {
                            monitor.wait();
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("We were interrupted");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        int totalThreads = 0;
        try {
            totalThreads = Integer.parseInt(args[0]);

        } catch (Exception exception) {
            System.out.println("Wrong input arguments" + exception.toString());
            System.exit(1);
        }
        for (int i = 0; i < totalThreads; i++) {
            CounterRunner thread = new CounterRunner(i, totalThreads);
            thread.start();
        }
    }
}