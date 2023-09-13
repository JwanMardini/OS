import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SharedResource {
    private int sharedCounter = 0;
    private Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock(); // Acquire the lock to enter the critical section
        try {
            sharedCounter++; // Critical section: Increment the shared counter
        } finally {
          lock.unlock(); // Release the lock when done with the critical section
        }
    }

    public int getSharedCounter() {
        return sharedCounter;
    }
}

public class MutexLockExample {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        // Create multiple threads to increment the shared counter
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                sharedResource.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                sharedResource.increment();
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                sharedResource.increment();
            }
        });

        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                sharedResource.increment();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Shared Counter: " + sharedResource.getSharedCounter());
    }
}
