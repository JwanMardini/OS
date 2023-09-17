// import java.util.LinkedList;

// class BoundedBuffer {
//     private LinkedList<Integer> buffer;
//     private int maxSize;

//     public BoundedBuffer(int size) {
//         buffer = new LinkedList<>();
//         maxSize = size;
//     }

//     public synchronized void produce(int item) throws InterruptedException {
//         while (buffer.size() == maxSize) {
//             // Buffer is full, wait for a consumer to remove items
//             wait();
//         }
        
//         buffer.add(item);
//         System.out.println("Produced: " + item);
//         notifyAll(); // Notify waiting consumers that a new item is available
//     }

//     public synchronized int consume() throws InterruptedException {
//         while (buffer.isEmpty()) {
//             // Buffer is empty, wait for a producer to add items
//             wait();
//         }
        
//         int item = buffer.removeFirst();
//         System.out.println("Consumed: " + item);
//         notifyAll(); // Notify waiting producers that space is available
//         return item;
//     }
// }

// class Producer extends Thread {
//     private BoundedBuffer buffer;

//     public Producer(BoundedBuffer buffer) {
//         this.buffer = buffer;
//     }

//     @Override
//     public void run() {
//         try {
//             for (int i = 1; i <= 10; i++) {
//                 buffer.produce(i);
//                 sleep(100); // Simulate some production time
//             }
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//     }
// }

// class Consumer extends Thread {
//     private BoundedBuffer buffer;

//     public Consumer(BoundedBuffer buffer) {
//         this.buffer = buffer;
//     }

//     @Override
//     public void run() {
//         try {
//             for (int i = 1; i <= 10; i++) {
//                 buffer.consume();
//                 sleep(150); // Simulate some consumption time
//             }
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//     }
// }

// public class BoundedBufferExampleSolved {
//     public static void main(String[] args) {
//         BoundedBuffer buffer = new BoundedBuffer(5); // Buffer size is 5

//         Producer producer1 = new Producer(buffer);
//         Producer producer2 = new Producer(buffer);
//         Consumer consumer1 = new Consumer(buffer);
//         Consumer consumer2 = new Consumer(buffer);

//         producer1.start();
//         producer2.start();
//         consumer1.start();
//         consumer2.start();
//     }
// }

import java.util.LinkedList;
import java.util.Queue;

class BoundedBuffer {
    private Queue<Integer> buffer;
    private int maxSize;

    public BoundedBuffer(int size) {
        buffer = new LinkedList<>();
        maxSize = size;
    }

    public synchronized void produce(int item) throws InterruptedException {
        while (buffer.size() == maxSize) {
            // Buffer is full, wait for a consumer to make space
            wait();
        }

        buffer.add(item);
        System.out.println("Produced: " + item);

        // Notify a waiting consumer that an item is available
        notifyAll();
    }

    public synchronized int consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            // Buffer is empty, wait for a producer to add an item
            wait( );
        }

        int item = buffer.poll();
        System.out.println("Consumed: " + item);

        // Notify a waiting producer that space is available
        notifyAll();

        return item;
    }
}

class Producer extends Thread {
    private BoundedBuffer buffer;

    public Producer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                buffer.produce(i);
                sleep(100); // Simulate some production time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread {
    private BoundedBuffer buffer;

    public Consumer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                buffer.consume();
                sleep(150); // Simulate some consumption time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class BoundedBufferExampleSolved {
    public static void main(String[] args) {
        BoundedBuffer buffer = new BoundedBuffer(5); // Buffer size is 5

        Producer producer1 = new Producer(buffer);
        Producer producer2 = new Producer(buffer);
        Consumer consumer1 = new Consumer(buffer);
        Consumer consumer2 = new Consumer(buffer);

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
    }
}
