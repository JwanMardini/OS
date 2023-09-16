class Peterson {
    private volatile boolean[] flag = new boolean[2];
    private volatile int turn; // Change variable name to 'turn'

    public void lock(int threadId) {
        int otherThread = 1 - threadId; // ID of the other thread

        // Announce that you want to enter the critical section
        flag[threadId] = true;
        turn = threadId; // Change 'victim' to 'turn'

        // Wait for the other thread to finish or give up its desire to enter
        while (flag[otherThread] && turn == threadId) {
            // Spin and wait
        }
    }

    public void unlock(int threadId) {
        // Release the lock
        flag[threadId] = false;
    }
}

class MyThread extends Thread {
    private static Peterson lock = new Peterson();
    private int threadId;

    public MyThread(int id) {
        threadId = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            lock.lock(threadId);
            // Critical section: Access the shared resource
            System.out.println("Thread " + threadId + " is in the critical section.");
            lock.unlock(threadId);
        }
    }
}

public class PetersonAlgorithmExample {
    public static void main(String[] args) {
        MyThread thread0 = new MyThread(0);
        MyThread thread1 = new MyThread(1);

        thread0.start();
        thread1.start();
    }
}
