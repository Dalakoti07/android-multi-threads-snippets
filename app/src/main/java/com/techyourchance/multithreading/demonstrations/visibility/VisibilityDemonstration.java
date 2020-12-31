package com.techyourchance.multithreading.demonstrations.visibility;

public class VisibilityDemonstration {

    // dont need to make it volatile we are going with syncronization
    private static int sCount = 0;
    private static final Object LOCK=new Object();

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
                synchronized (LOCK){
                    if (localValue != sCount) {
                        System.out.println("Consumer: detected count change " + sCount);
                        localValue = sCount;
                    }
                    if (sCount >= 5) {
                        break;
                    }
                }
            }
            System.out.println("Consumer: terminating");
        }
    }

    static class Producer extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (LOCK){
                    int localValue = sCount;
                    localValue++;
                    System.out.println("Producer: incrementing count to " + localValue);
                    sCount = localValue;
                    if(sCount>=5)
                        break;
                }
            }
            System.out.println("Producer: terminating");
        }
    }

}
