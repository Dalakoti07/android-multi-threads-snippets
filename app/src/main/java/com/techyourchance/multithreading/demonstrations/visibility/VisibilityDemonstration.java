package com.techyourchance.multithreading.demonstrations.visibility;

public class VisibilityDemonstration {

    private static class Counter{
        private volatile int mCount=0;
    }

    private static Counter sCount = new Counter();

    public static void main(String[] args) {
        new Consumer().start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            return;
        }
        new Producer().start();
    }

    static class Consumer extends Thread {
        @Override
        public void run() {
            int localValue = -1;
            while (true) {
                if (localValue != sCount.mCount) {
                    System.out.println("Consumer: detected count change " + sCount.mCount);
                    localValue = sCount.mCount;
                }
                if (sCount.mCount >= 5) {
                    break;
                }
            }
            System.out.println("Consumer: terminating");
        }
    }

    static class Producer extends Thread {
        @Override
        public void run() {
            while (true) {
                if(sCount.mCount>=5)
                    break;
                int localValue = sCount.mCount;
                localValue++;
                System.out.println("Producer: incrementing count to " + localValue);
                sCount.mCount = localValue;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Producer: terminating");
        }
    }

}
