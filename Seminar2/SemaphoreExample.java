import java.util.concurrent.Semaphore;

class SharedResource {
    private int sharedCounter = 0;
    private Semaphore semaphore = new Semaphore(1); // Initialize with one permit

    public void increment() {
        try {
            semaphore.acquire(); // Acquire the semaphore to enter the critical section
            sharedCounter++; // Critical section: Increment the shared counter
       } catch (InterruptedException e) {
         e.printStackTrace();
      } finally {
            semaphore.release(); // Release the semaphore when done with the critical section
       }
    }

    public int getSharedCounter() {
        return sharedCounter;
    }
}

public class SemaphoreExample {
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

        System.out.println("Shared Counter (with semaphore synchronization): " + sharedResource.getSharedCounter());
    }
}
